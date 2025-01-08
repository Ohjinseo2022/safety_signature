import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:safety_signature_app/common/const/color.dart';

class CustomTextFormField extends StatefulWidget {
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
  State<CustomTextFormField> createState() => _CustomTextFormFieldState();
}

class _CustomTextFormFieldState extends State<CustomTextFormField> {
  final TextEditingController _controller = TextEditingController();

  @override
  void initState() {
    // TODO: implement initState
    if (widget.value != null && widget.value != "") {
      _controller.text = widget.value!;
    }

    super.initState();
  }

  @override
  void dispose() {
    // 메모리 누수를 방지하기 위해 컨트롤러 해제
    _controller.dispose();
    super.dispose();
  }

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
          controller: _controller,
          // initialValue: widget.value,
          cursorColor: TEXT_COLOR,
          //비밀번호 입력할때 사용
          obscureText: widget.obscureText,
          autofocus: widget.autofocus,
          onChanged: widget.onChanged,
          inputFormatters: widget.inputFormatters,
          //입력 필드의 데코레이션
          decoration: InputDecoration(
            labelText: widget.labelText,
            contentPadding: EdgeInsets.symmetric(horizontal: 5),
            //placeholder
            enabled: widget.enabled, // 활성화 비활성화
            hintText: widget.hintText,
            errorText: widget.errorText,

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
        if (widget.value != null && widget.value != "" && widget.enabled)
          Positioned(
            right: 0,
            child: IconButton(
                onPressed: () {
                  _controller.clear();
                  _controller.text = '';
                  // setState(() {});
                  if (widget.onChanged != null) widget.onChanged!("");
                },
                icon: Icon(
                  Icons.clear,
                )),
          )
      ],
    );
  }
}
