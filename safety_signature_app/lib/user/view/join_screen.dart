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
  String userId = "";
  String password = "";
  String passwordCheck = "";
  String middleNumber = "";
  String lastNumber = "";
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
                placeholder: "이름을 입력해 주세요.",
                onChangedValue: (value) {
                  setState(() {
                    name = value;
                  });
                }),
            Padding(
              padding:
                  const EdgeInsets.only(left: 20, right: 20, top: 5, bottom: 0),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  _phoneNumber(
                      inputValue: "",
                      onChangedValue: (value) {
                        print(value);
                      }),
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
                    inputValue: lastNumber,
                  ),
                ],
              ),
            ),
            _inputField(
              inputValue: userId,
              title: "아이디(이메일)",
            ),
            _inputField(
              inputValue: password,
              title: "비밀번호",
            ),
            _inputField(
              inputValue: passwordCheck,
              title: "비밀번호 확인",
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
      String? placeholder,
      Function? onChangedValue,
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
                if (onChangedValue != null) onChangedValue(value);
              },
              value: inputValue,
              obscureText: obscureText,
              errorText: validation,
              hintText: placeholder ?? "입력해 주세요.",
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
    Function? onChangedValue,
  }) {
    return SizedBox(
      height: 80,
      width: 100,
      child: CustomTextFormField(
        onChanged: (value) {
          if (onChangedValue != null) onChangedValue(value);
          inputValue = value;
        },
        value: inputValue,
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
