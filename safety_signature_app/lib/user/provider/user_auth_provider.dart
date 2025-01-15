import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:kakao_flutter_sdk/kakao_flutter_sdk.dart';
import 'package:safety_signature_app/common/const/data.dart';
import 'package:safety_signature_app/common/model/login_response.dart';
import 'package:safety_signature_app/common/secure_storage/secure_storage.dart';
import 'package:safety_signature_app/user/model/login_req_model.dart';
import 'package:safety_signature_app/user/model/post_join_body.dart';
import 'package:safety_signature_app/user/model/user_model.dart';
import 'package:safety_signature_app/user/repository/auth_repository.dart';
import 'package:safety_signature_app/user/repository/user_master_repository.dart';

final userAuthProvider =
    StateNotifierProvider<UserAuthStateNotifier, UserModelBase?>((ref) {
  final authRepository = ref.watch(authRepositoryProvider);
  final userMasterRepository = ref.watch(userMasterRepositoryProvider);
  final storage = ref.watch(secureStorageProvider);
  return UserAuthStateNotifier(
      authRepository: authRepository,
      userMasterRepository: userMasterRepository,
      storage: storage);
});

class UserAuthStateNotifier extends StateNotifier<UserModelBase?> {
  final AuthRepository authRepository;
  final UserMasterRepository userMasterRepository;
  final FlutterSecureStorage storage;
  final GoogleSignIn _googleSignIn =
      GoogleSignIn(scopes: ["email", "profile", "openid"]);

  UserAuthStateNotifier(
      {required this.authRepository,
      required this.userMasterRepository,
      required this.storage})
      : super(UserModelLoading()) {
    getProfile();
  }
//String? email, String? password
  //LoginResponse  테스트 완료하고 이거로 변경 UserModelBase
  Future<UserModelBase?> login({required String platform}) async {
    try {
      bool isToken =
          platform == 'google' ? await _onGoogleLogin() : await _onKakaoLogin();
      if (isToken) {
        bool isProfile = state is UserMinModel;
        var pState = null;
        if (isProfile) {
          pState = state as UserMinModel;
        }
        state = UserModelLoading();

        final response = await authRepository.login(
            loginReqDTO: LoginReqModel(
          socialType: platform,
          name: isProfile ? pState?.name : null,
          email: isProfile ? pState?.email : null,
        )); //email: email, password: password
        print("accessToken : ${response.accessToken}");
        print("refreshToken : ${response.refreshToken}");
        // 소셜 로그인 완료 후
        await storage.write(key: ACCESS_TOKEN_KEY, value: response.accessToken);
        await storage.write(
            key: REFRESH_TOKEN_KEY, value: response.refreshToken);
        final userData = await userMasterRepository.userProfile();
        state = userData as UserModelBase?;
        return userData;
      } else {
        state = UserModelError(message: "소셜 로그인 중 에러가 발생 했습니다.");
        return Future.value(state);
      }
    } catch (e) {
      state = UserModelError(message: e.toString());
      return Future.value(state);
    }
  }

  Future<void> getProfile() async {
    final refreshToken = await storage.read(key: REFRESH_TOKEN_KEY);
    final accessToken = await storage.read(key: ACCESS_TOKEN_KEY);
    state = UserModelLoading();
    if (refreshToken == null || accessToken == null) {
      state = UserModelGuest();
      return;
    }
    try {
      final response = await userMasterRepository.userProfile();
      if (response == null) {
        state = UserModelGuest();
        return;
      }
      state = response;
    } catch (e) {
      state = UserModelError(message: e.toString());
    }
  }

  Future<void> userLogout() async {
    await storage.delete(key: ACCESS_TOKEN_KEY);
    await storage.delete(key: REFRESH_TOKEN_KEY);
    // TODO : 데이터 베이스에 저장돼있는 토큰정보도 같이 삭제 필요함.
    state = UserModelGuest();
  }

  Future<LoginResponseBase?> userJoin(PostJoinBody postJoinBody) async {
    state = UserModelLoading();
    try {
      final response =
          await userMasterRepository.userJoin(postJoinBody: postJoinBody);
      if (response is LoginResponse) {
        await storage.write(key: ACCESS_TOKEN_KEY, value: response.accessToken);
        await storage.write(
            key: REFRESH_TOKEN_KEY, value: response.refreshToken);
        await getProfile();
      }
      if (response is JoinFailedResponse) {
        state = UserModelError(message: response.message);
      }
      return response;
    } catch (e) {
      print("join error ${e.toString()}");
      state = UserModelError(message: e.toString());
      return null;
    }
  }

  Future<bool> _onKakaoLogin() async {
    try {
      // User user = await UserApi.instance.me();
      // print(user);
      OAuthToken token =
          await isKakaoTalkInstalled() //true 카카오톡 실행가능 false 면 실행불가능
              // 카카오톡 실행가능 여부 확인해서 웹으로 실행할지 카카오톡 앱을 실행할지 지정
              ? await UserApi.instance.loginWithKakaoTalk()
              : await UserApi.instance
                  .loginWithKakaoAccount(); //loginWithKakaoTalk   loginWithKakaoAccount
      print('카카오계정으로 로그인 성공 ${token.accessToken}');
      print('카카오계정 ${token}');
      String accessToken = token.accessToken;
      // storage.write(key: ACCESS_TOKEN_KEY, value: accessToken);
      await storage.write(key: ACCESS_TOKEN_KEY, value: accessToken);
      // final loginResponse = await
      return true;
    } catch (error) {
      print('카카오계정으로 로그인 실패 $error');
      return false;
    }
  }

  Future<bool> _onGoogleLogin() async {
    try {
      await _googleSignIn.signIn();
      final GoogleSignInAuthentication googleSignInAuthentication =
          await _googleSignIn.currentUser!.authentication;

      final String? accessToken = googleSignInAuthentication.accessToken;
      await storage.write(key: ACCESS_TOKEN_KEY, value: accessToken);
      return true;
    } catch (error) {
      print('Error during Google login: $error');
      return false;
    }
  }

  // Future<bool> _onNaverLogin() async {
  //   try {
  //     final NaverLoginResult naver = await FlutterNaverLogin.logIn();
  //     // await storage.write(
  //     //     key: ACCESS_TOKEN_KEY, value: naver.accessToken.toString());
  //     state = UserMinModel(
  //       id: naver.account.id,
  //       email: naver.account.email,
  //       profileImageUri: null,
  //       name: naver.account.name,
  //     );
  //     print('accessToken = ${naver.accessToken}');
  //     print('id = ${naver.account.id}');
  //     print('email = ${naver.account.email}');
  //     print('name = ${naver.account.name}');
  //     print('mobile = ${naver.account.mobile}');
  //     return true;
  //   } catch (error) {
  //     print('Error during Naver Login : $error');
  //     return false;
  //   }
  // }
}
