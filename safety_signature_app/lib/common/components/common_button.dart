import 'package:flutter/material.dart';
import 'package:safety_signature_app/common/const/color.dart';

class CommonButton extends StatelessWidget {
  final String label;
  final VoidCallback onPressed;
  final EdgeInsetsGeometry? padding;
  const CommonButton(
      {super.key, required this.label, required this.onPressed, this.padding});

  @override
  Widget build(BuildContext context) {
    ButtonStyle elevatedButtonStyle = ElevatedButton.styleFrom(
      backgroundColor: PRIMARY_COLOR,
      disabledBackgroundColor: SUBTEXT_COLOR,
    );
    return ElevatedButton(
        style: elevatedButtonStyle.copyWith(
            padding: ButtonStyleButton.allOrNull<EdgeInsetsGeometry>(padding)),
        onPressed: onPressed,
        child: Text(
          label,
          style: defaultTextStyle,
        ));
  }
}
