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
  final InputBorder? border;
  final ValueChanged<String>? onChanged;
  final List<TextInputFormatter>? inputFormatters; // 입력 제한 설정
  final FormFieldValidator<String>? validator;
  final Icon? prefixIcon;
  final Color? fillColor;
  final Function? enterOrReturnKeyFn;
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
    this.validator,
    this.border,
    this.prefixIcon,
    this.fillColor,
    this.enterOrReturnKeyFn,
    required this.onChanged,
  });

  @override
  State<CustomTextFormField> createState() => _CustomTextFormFieldState();
}

class _CustomTextFormFieldState extends State<CustomTextFormField> {
  final TextEditingController _controller = TextEditingController();
  bool obscureText = false;
  @override
  void initState() {
    // TODO: implement initState
    if (widget.value != null && widget.value != "") {
      _controller.text = widget.value!;
    }
    obscureText = widget.obscureText;
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
          autovalidateMode: widget.validator != null
              ? AutovalidateMode.onUserInteraction
              : null,

          controller: _controller,
          // initialValue: widget.value,
          cursorColor: TEXT_COLOR,
          //비밀번호 입력할때 사용
          obscureText: obscureText,
          autofocus: widget.autofocus,
          onChanged: (value) {
            setState(() {
              if (widget.onChanged != null) widget.onChanged!(_controller.text);
              // _controller.text = value;
            });
          },
          onFieldSubmitted: (value) async {
            await widget.enterOrReturnKeyFn;
          },
          inputFormatters: widget.inputFormatters,
          validator: widget.validator,
          style: defaultTextStyle,
          //입력 필드의 데코레이션
          decoration: InputDecoration(
            prefixIcon: widget.prefixIcon,
            labelText: widget.labelText,
            labelStyle: TextStyle(color: SECONDARY_COLOR),
            contentPadding: EdgeInsets.symmetric(horizontal: 5),
            //placeholder
            enabled: widget.enabled, // 활성화 비활성화
            hintText: widget.hintText,
            errorText: widget.errorText,
            errorStyle: const TextStyle(overflow: TextOverflow.ellipsis),
            hintStyle: TextStyle(
              color: TEXT_COLOR.withOpacity(0.3),
              fontSize: 14.0,
            ),
            fillColor: widget.fillColor ??
                CARD_COLOR, // Colors.transparent, //SECONDARY_COLOR ,
            filled: true,
            //모든 input 상태의 기본 스타일 세팅
            border: widget.border ?? baseBorder,
            enabledBorder: widget.border ?? baseBorder,
            focusedBorder: widget.border ??
                baseBorder.copyWith(
                  borderSide: baseBorder.borderSide.copyWith(
                    color: TEXT_COLOR,
                  ),
                ),
          ),
        ),
        if (_controller.text != "" && widget.enabled && widget.obscureText)
          Positioned(
            right: 0,
            child: IconButton(
              onPressed: () {
                setState(() {
                  obscureText = !obscureText;
                });
              },
              icon: Icon(
                obscureText
                    ? Icons.visibility_outlined
                    : Icons.visibility_off_outlined,
              ),
            ),
          ),
        if (_controller.text != "" && widget.enabled && !widget.obscureText)
          Positioned(
            right: 0,
            child: IconButton(
              onPressed: () {
                setState(() {
                  _controller.clear();
                  _controller.text = '';
                  if (widget.onChanged != null) widget.onChanged!("");
                });
              },
              icon: Icon(
                Icons.clear,
              ),
            ),
          ),
      ],
    );
  }
}
