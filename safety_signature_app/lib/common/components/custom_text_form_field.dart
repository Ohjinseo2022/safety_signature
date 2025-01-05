import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:safety_signature_app/common/const/color.dart';

class CustomTextFormField extends StatelessWidget {
  final String? value;
  final String? hintText;
  final String? errorText;
  final bool obscureText;
  final bool autofocus;
  final bool enabled;
  final String? labelText;
  final ValueChanged<String>? onChanged;
  final List<TextInputFormatter>? inputFormatters; // 입력 제한 설정
  CustomTextFormField({
    super.key,
    required this.value,
    this.hintText,
    this.errorText,
    this.labelText,
    this.enabled = true,
    this.obscureText = false,
    this.autofocus = false,
    this.inputFormatters,
    required this.onChanged,
  });

  @override
  Widget build(BuildContext context) {
    // UnderlineInputBorder() -> 기본값임
    final baseBorder = UnderlineInputBorder();
    // UnderlineInputBorder(
    //   borderSide: BorderSide(
    //     color: PRIMARY_COLOR,
    //     width: 2.0,
    //   ),
    // );
    return Stack(
      children: [
        TextFormField(
          cursorColor: TEXT_COLOR,
          //비밀번호 입력할때 사용
          obscureText: obscureText,
          autofocus: autofocus,
          onChanged: onChanged,
          inputFormatters: inputFormatters,
          //입력 필드의 데코레이션
          decoration: InputDecoration(
            labelText: labelText,
            contentPadding: EdgeInsets.symmetric(horizontal: 5),
            //placeholder
            enabled: enabled, // 활성화 비활성화
            hintText: hintText,
            errorText: errorText,

            // errorStyle: const TextStyle(
            //   height: 0,
            //   fontSize: 0,
            // ),
            hintStyle: TextStyle(
              color: TEXT_COLOR.withOpacity(0.3),
              fontSize: 14.0,
            ),
            fillColor: SECONDARY_COLOR,
            filled: true,
            //모든 input 상태의 기본 스타일 세팅
            border: baseBorder,
            enabledBorder: baseBorder,
            focusedBorder: baseBorder.copyWith(
              borderSide: baseBorder.borderSide.copyWith(
                color: TEXT_COLOR,
              ),
            ),
          ),
        ),
        if (value != null && value != "")
          Positioned(
            right: 0,
            child: IconButton(
                onPressed: () {},
                icon: Icon(
                  Icons.clear,
                )),
          )
      ],
    );
  }
}
