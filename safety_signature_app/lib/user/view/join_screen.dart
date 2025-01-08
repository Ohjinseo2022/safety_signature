import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:safety_signature_app/common/components/custom_text_form_field.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/layout/default_layout.dart';
import 'package:safety_signature_app/user/model/user_model.dart';
import 'package:safety_signature_app/user/provider/user_auth_provider.dart';

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
  String mobile = "010";

  @override
  Widget build(BuildContext context) {
    final state = ref.watch(userAuthProvider);

    if (state is UserMinModel) {
      setState(() {
        name = state.name;
        userId = state.email;
        mobile = state.mobile ?? "010";
      });
    }

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
              },
              enabled: state is! UserMinModel,
            ),
            _inputField(
                inputValue: mobile,
                title: "핸드폰 번호",
                placeholder: "핸드폰 번호를 입력해 주세요. 숫자만 입력해 주세요",
                onChangedValue: (value) {
                  setState(() {
                    mobile = value;
                  });
                },
                inputFormatters: [
                  FilteringTextInputFormatter.digitsOnly,
                  LengthLimitingTextInputFormatter(11),
                ]),
            _inputField(
              inputValue: userId,
              title: "아이디(이메일)",
              placeholder: "아이디(이메일)을 입력해 주세요. ex)xxx@gmail.com",
              enabled: state is! UserMinModel,
            ),
            _inputField(
              inputValue: password,
              title: "비밀번호",
              placeholder: "비밀번호를 입력해 주세요.",
              onChangedValue: (value) {
                setState(() {
                  password = value;
                });
              },
            ),
            if (password != "")
              _inputField(
                inputValue: passwordCheck,
                title: "비밀번호 확인",
                placeholder: "비밀번호를 다시한번 입력해주세요.",
                onChangedValue: (value) {
                  setState(() {
                    passwordCheck = value;
                  });
                },
              ),
          ],
        ),
      ),
    );
  }

  _inputField({
    String? inputValue,
    String? validation,
    String? title,
    String? placeholder,
    Function? onChangedValue,
    List<TextInputFormatter>? inputFormatters,
    bool obscureText = false,
    bool? enabled,
  }) {
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
              enabled: enabled ?? true,
              obscureText: obscureText,
              errorText: validation,
              hintText: placeholder ?? "입력해 주세요.",
              inputFormatters: inputFormatters,
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
