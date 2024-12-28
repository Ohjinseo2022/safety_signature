import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:safety_signature_app/common/components/custom_text_form_field.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/layout/default_layout.dart';

class JoinScreen extends ConsumerStatefulWidget {
  static String get routeName => "JoinScreen";
  const JoinScreen({super.key});

  @override
  ConsumerState<JoinScreen> createState() => _JoinScreenState();
}

class _JoinScreenState extends ConsumerState<JoinScreen> {
  String name = "";
  String middleNumber = "";
  String endNumber = "";
  @override
  Widget build(BuildContext context) {
    return DefaultLayout(
      title: "회원가입",
      backgroundColor: SECONDARY_COLOR,
      topAppBarBtn: false,
      child: Center(
        child: ListView(
          children: [
            _inputField(
              inputValue: name,
              title: "이름",
            ),
            // _inputField(
            //   inputValue: name,
            //   title: "생년월일",
            // ),

            Padding(
              padding:
                  const EdgeInsets.only(left: 20, right: 20, top: 5, bottom: 0),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  _phoneNumber(
                    inputValue: middleNumber,
                  ),
                  SizedBox(
                    width: 20,
                    height: 80,
                    child: Center(
                      child: Text(
                        "-",
                        style: defaultTextStyle,
                      ),
                    ),
                  ),
                  _phoneNumber(
                    inputValue: middleNumber,
                  ),
                  SizedBox(
                    width: 20,
                  ),
                  _phoneNumber(
                    inputValue: middleNumber,
                  ),
                ],
              ),
            ),

            _inputField(
              inputValue: name,
              title: "아이디(이메일)",
            ),
            _inputField(
              inputValue: name,
              title: "비밀번호",
            ),
          ],
        ),
      ),
    );
  }

  _inputField(
      {required String inputValue,
      String? validation,
      String? title,
      bool obscureText = false}) {
    return Padding(
      padding: const EdgeInsets.only(left: 20, right: 20, top: 5, bottom: 0),
      child: Column(
        children: [
          if (title != null)
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Container(
                  alignment: Alignment.bottomLeft,
                  child: Text(
                    title,
                    style: defaultTextStyle.copyWith(fontSize: 18),
                  )),
            ),
          SizedBox(
            height: 80,
            child: CustomTextFormField(
              onChanged: (value) {
                inputValue = value;
              },
              obscureText: obscureText,
              errorText: validation,
              hintText: title != null ? "$title을/를 입력해 주세요." : "입력해 주세요.",
            ),
          ),
        ],
      ),
    );
  }

  _phoneNumber({
    required String inputValue,
    String? validation,
    String? title,
  }) {
    return SizedBox(
      height: 80,
      width: 100,
      child: CustomTextFormField(
        onChanged: (value) {
          inputValue = value;
        },
        errorText: validation,
        hintText: "0000",
        inputFormatters: [
          FilteringTextInputFormatter.digitsOnly,
          LengthLimitingTextInputFormatter(4),
        ],
      ),
    );
  }
}
