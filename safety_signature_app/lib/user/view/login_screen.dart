import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:go_router/go_router.dart';
import 'package:safety_signature_app/common/components/common_dialog.dart';
import 'package:safety_signature_app/common/components/login_button.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/enumeration/social.dart';
import 'package:safety_signature_app/user/model/user_model.dart';
import 'package:safety_signature_app/user/provider/user_auth_provider.dart';
import 'package:safety_signature_app/user/view/email_login_screen.dart';
import 'package:safety_signature_app/user/view/join_screen.dart';

class LoginScreen extends ConsumerStatefulWidget {
  static String get routeName => "loginScreen";
  final AnimationController? animationController;
  const LoginScreen({
    super.key,
    this.animationController,
  });

  @override
  ConsumerState<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends ConsumerState<LoginScreen>
    with SingleTickerProviderStateMixin {
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
    if (state is UserMinModel) {
      widget.animationController?.animateTo(0);
    }
    if (state is UserModelError) {
      commonDialog(
          context: context,
          content: Text(
            state.message,
            style: defaultTextStyle,
          ),
          onConfirm: () {});
    }
    return Center(
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
          SizedBox(
            height: 15,
            width: double.infinity,
          ),
          _googleLoginButton(onPressed: () async {
            UserModelBase? model = await onLoginHandler(
                context: context, platform: SocialTypeCode.GOOGLE.code);
          }),
          SizedBox(
            height: 15,
          ),
          _kakaoLoginButton(onPressed: () async {
            UserModelBase? model = await onLoginHandler(
                context: context, platform: SocialTypeCode.KAKAO.code);
          }),
          SizedBox(
            height: 15,
          ),
          _emailLoginButton(onPressed: () async {
            context.pushNamed(EmailLoginScreen.routeName);
          }),
          _emailJoin(onPressed: () {
            context.pushNamed(JoinScreen.routeName);
            widget.animationController?.animateTo(0);
          }),
          // _NaverLoginButton(onPressed: () async {
          //   await onLoginHandler(
          //       context: context, platform: SocialTypeCode.NAVER.code);
          // }),
        ],
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

Future<T?> loginBottomSheet<T>(
    BuildContext context, AnimationController transitionAnimationController) {
  return showModalBottomSheet(
    transitionAnimationController: transitionAnimationController,
    backgroundColor: Colors.transparent,
    context: context,
    builder: (context) {
      return Container(
          height: MediaQuery.of(context).size.height,
          // padding: EdgeInsets.only(bottom: 50),
          margin: const EdgeInsets.only(
            left: 25,
            right: 25,
            bottom: 40,
          ),
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(50),
            border: Border.all(
              color: SECONDARY_COLOR.withOpacity(0.3),
              width: 1,
            ),
            color: TEXT_COLOR, // BACK_GROUND_COLOR.withOpacity(0.8),
          ),
          child:
              LoginScreen(animationController: transitionAnimationController));
    },
  );
}
