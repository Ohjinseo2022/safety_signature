import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:go_router/go_router.dart';
import 'package:safety_signature_app/common/components/common_dialog.dart';
import 'package:safety_signature_app/common/components/custom_text_form_field.dart';
import 'package:safety_signature_app/common/components/login_button.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/enumeration/social.dart';
import 'package:safety_signature_app/common/view/root_tab.dart';
import 'package:safety_signature_app/user/model/login_req_model.dart';
import 'package:safety_signature_app/user/model/user_model.dart';
import 'package:safety_signature_app/user/provider/user_auth_provider.dart';
import 'package:safety_signature_app/user/view/email_login_screen.dart';
import 'package:safety_signature_app/user/view/join_screen.dart';

class LoginScreen extends ConsumerStatefulWidget {
  static String get routeName => "loginScreen";
  // final AnimationController? animationController;
  const LoginScreen({
    super.key,
    // this.animationController,
  });

  @override
  ConsumerState<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends ConsumerState<LoginScreen>
    with SingleTickerProviderStateMixin {
  String userId = '';
  String password = '';

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final state = ref.watch(userAuthProvider);
    // if (state is UserMinModel) {
    //   // widget.animationController ?? widget.animationController?.animateTo(0);
    // }
    // if (state is UserModelError) {
    //
    //   commonDialog(
    //       context: context,
    //       content: Text(
    //         state.message,
    //         style: defaultTextStyle,
    //       ),
    //       onConfirm: () {});
    // }

    Future<void> onEmailLoginHandler() async {
      // 로그인 액션
      if (userId == "" || password == "") {
        await commonDialog(
            context: context,
            title: "이메일을 입력해주세요",
            content: userId == ""
                ? Text(
                    "이메일 형식의 아이디를 입력해 주세요.",
                    style: defaultTextStyle,
                  )
                : Text(
                    "비밀번호를 입력해 주세요.",
                    style: defaultTextStyle,
                  ),
            onConfirm: () {});
        return;
      }
      bool isLogin = await ref.read(userAuthProvider.notifier).normalLogin(
          loginNormalReqModel:
              LoginNormalReqModel(userId: userId, password: password));
      if (isLogin) {
        mounted ? context.goNamed(RootTab.routeName) : null;
        return;
      } else {
        await commonDialog(
            context: context,
            title: "로그인 실패",
            content: Text(
              "아이디/비밀번호 를 확인해주세요.",
              style: defaultTextStyle,
            ),
            onConfirm: () {});
        return;
      }
    }

    return Center(
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 50.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            if (state is UserMinModel)
              Text(
                state.userStatusCode,
                style: defaultTextStyle,
              ),
            Text("안전싸인",
                style: defaultTextStyle.copyWith(
                    fontWeight: FontWeight.w800,
                    fontSize: 30,
                    color: GOOGLE_PRIMARY_COLOR)),
            Text(
              "로그인 방법을 선택하세요.",
              style: TextStyle(color: GOOGLE_PRIMARY_COLOR),
            ),
            //TODO : 구글 로그인 우선 삭제. 추후 추가
            // SizedBox(
            //   height: 15,
            //   width: double.infinity,
            // ),
            // _googleLoginButton(onPressed: () async {
            //   UserModelBase? model = await onLoginHandler(
            //       context: context, platform: SocialTypeCode.GOOGLE.code);
            // }),
            SizedBox(
              height: 15,
            ),
            _inputField(
              text: userId,
              onChanged: (value) => userId = value,
              prefixIcon: Icon(
                Icons.email,
                color: SECONDARY_COLOR,
              ),
              labelText: "이메일",
              hintText: "이메일 형식의 아이디를 입력해 주세요.",
            ),
            SizedBox(
              height: 15,
            ),
            _inputField(
                text: password,
                onChanged: (value) => password = value,
                prefixIcon: Icon(
                  Icons.key_outlined,
                  color: SECONDARY_COLOR,
                ),
                labelText: "비밀번호",
                hintText: "비밀번호를 입력해주세요",
                obscureText: true,
                enterOrReturnKeyFn: () async {
                  await onEmailLoginHandler();
                }),
            SizedBox(height: 24),
            // SizedBox(
            //   height: 15,
            // ),
            _emailLoginButton(onPressed: () async {
              await onEmailLoginHandler();
            }),
            SizedBox(
              height: 15,
            ),
            _kakaoLoginButton(onPressed: () async {
              UserModelBase? model = await onLoginHandler(
                  context: context, platform: SocialTypeCode.KAKAO.code);
            }),
            // _emailJoin(onPressed: () {
            //   context.pushNamed(JoinScreen.routeName);
            //   widget.animationController?.animateTo(0);
            // }),
            // _NaverLoginButton(onPressed: () async {
            //   await onLoginHandler(
            //       context: context, platform: SocialTypeCode.NAVER.code);
            // }),
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                // _underLineTextBtn(
                //     onPressed: () {},
                //     text: "비밀번호를 잊어버렸나요?"), // TODO: 본인인증 기능 추가되면 추가하기
                _underLineTextBtn(
                    onPressed: () {
                      context.pushNamed(JoinScreen.routeName);
                      // widget.animationController?.animateTo(0);
                    },
                    text: "이메일로 회원가입"),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Future<UserModelBase?> onLoginHandler(
      {required String platform, required BuildContext context}) async {
    UserModelBase? model =
        await ref.read(userAuthProvider.notifier).login(platform: platform);
    return model;
  }

  Widget _googleLoginButton({required VoidCallback? onPressed}) {
    return LoginButton(
      onPressed: onPressed,
      svgPath: 'asset/svg/btn_google2.svg',
      // iconHeight: 25,
      label: "Google로 로그인",
      backgroundColor: GOOGLE_PRIMARY_COLOR,
      labelColor: KAKAO_LABEL_COLOR,
    );
  }

  Widget _kakaoLoginButton({required VoidCallback? onPressed}) {
    return LoginButton(
      onPressed: onPressed,
      svgPath: 'asset/svg/btn_kakao.svg',
      label: "KaKao로 로그인",
      backgroundColor: KAKAO_CONTAINER_COLOR,
      labelColor: KAKAO_LABEL_COLOR,
    );
  }

  Widget _emailLoginButton({required VoidCallback? onPressed}) {
    return LoginButton(
      onPressed: onPressed,
      svgPath: 'asset/svg/btn_email.svg',
      label: "Email로 로그인",
      iconHeight: 30,
      backgroundColor: GOOGLE_PRIMARY_COLOR,
      labelColor: KAKAO_LABEL_COLOR,
    );
  }
  // Widget _NaverLoginButton({required VoidCallback? onPressed}) {
  //   return LoginButton(
  //       onPressed: onPressed,
  //       svgPath: 'asset/svg/btn_naver.svg',
  //       label: 'Naver로 로그인',
  //       backgroundColor: NAVER_PRIMARY_COLOR);
  // }
}

Widget _emailJoin({required VoidCallback? onPressed}) {
  return TextButton(
    onPressed: onPressed,
    style: TextButton.styleFrom(
      padding: EdgeInsets.all(5.0),
      foregroundColor: TEXT_COLOR,
    ),
    child: Column(
      children: [
        Text(
          "이메일로 회원가입",
          style: defaultTextStyle.copyWith(
            fontSize: 12,
            color: SECONDARY_COLOR,
          ),
        ),
        SizedBox(
          height: 3,
        ),
        Container(
          height: 1.0,
          width: 100.0,
          color: SECONDARY_COLOR,
        ),
      ],
    ),
  );
}

_inputField({
  required String text,
  required ValueChanged<String> onChanged,
  required Icon prefixIcon,
  bool obscureText = false,
  Function? enterOrReturnKeyFn,
  String? labelText,
  String? hintText,
}) {
  return Container(
    height: 50,
    child: CustomTextFormField(
        hintText: hintText ?? "입력해 주세요.",
        labelText: labelText,
        value: text,
        onChanged: onChanged,
        prefixIcon: prefixIcon,
        obscureText: obscureText,
        enterOrReturnKeyFn: enterOrReturnKeyFn
        // autofocus: true,
        ),
  );
}

Widget _underLineTextBtn(
    {required VoidCallback? onPressed, required String text}) {
  return TextButton(
    onPressed: onPressed,
    style: TextButton.styleFrom(
      padding: EdgeInsets.all(5.0),
      foregroundColor: TEXT_COLOR,
    ),
    child: Column(
      children: [
        Text(
          text,
          style: defaultTextStyle.copyWith(
            fontSize: 12,
            color: SECONDARY_COLOR,
          ),
        ),
        SizedBox(
          height: 3,
        ),
        Container(
          height: 1.0,
          width: text.replaceAll(" ", "").length * 11.5,
          color: SECONDARY_COLOR,
        ),
      ],
    ),
  );
}
