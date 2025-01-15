import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:safety_signature_app/common/components/custom_text_form_field.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/layout/default_layout.dart';

class EmailLoginScreen extends ConsumerStatefulWidget {
  static String get routeName => "emailLoginScreen";
  const EmailLoginScreen({super.key});

  @override
  ConsumerState<EmailLoginScreen> createState() => _EmailLoginScreenState();
}

class _EmailLoginScreenState extends ConsumerState<EmailLoginScreen> {
  String userId = '';
  String password = '';
  @override
  Widget build(BuildContext context) {
    return DefaultLayout(
        title: "안전싸인 로그인",
        topAppBarBtn: false,
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 50),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              _inputField(
                text: userId,
                onChanged: (value) => userId = value,
                prefixIcon: Icon(Icons.email),
                labelText: "아이디",
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
                  color: PRIMARY_COLOR,
                ),
                labelText: "비밀번호",
                hintText: "비밀번호를 입력해주세요",
                obscureText: true,
              ),
              SizedBox(height: 24),
              // 로그인 버튼
              ElevatedButton(
                onPressed: () {
                  // 로그인 액션
                },
                child: Text('Login'),
                style: ElevatedButton.styleFrom(
                  padding: EdgeInsets.symmetric(vertical: 16),
                ),
              ),
              SizedBox(height: 16),
              // 비밀번호 찾기 & 회원가입 링크
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  _underLineTextBtn(onPressed: () {}, text: "비밀번호를 잊어버렸나요?"),
                  _underLineTextBtn(onPressed: () {}, text: "회원가입"),
                ],
              ),
            ],
          ),
        ));
  }
}

_inputField({
  required String text,
  required ValueChanged<String> onChanged,
  required Icon prefixIcon,
  bool obscureText = false,
  String? labelText,
  String? hintText,
}) {
  return Padding(
    padding: EdgeInsets.all(1.0),
    child: Container(
      height: 50,
      child: CustomTextFormField(
          hintText: hintText ?? "입력해 주세요.",
          labelText: labelText,
          value: text,
          onChanged: onChanged,
          prefixIcon: prefixIcon,
          obscureText: obscureText
          // autofocus: true,
          ),
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
            color: PRIMARY_COLOR,
          ),
        ),
        SizedBox(
          height: 3,
        ),
        Container(
          height: 1.0,
          width: text.replaceAll(" ", "").length * 12,
          color: SECONDARY_COLOR,
        ),
      ],
    ),
  );
}
