import 'package:flutter/material.dart';
import 'package:safety_signature_app/common/const/color.dart';

class CardContainer extends StatelessWidget {
  final Widget child;
  const CardContainer({super.key, required this.child});

  @override
  Widget build(BuildContext context) {
    return Card(
      color: CARD_COLOR,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(8.0),
        side: BorderSide(color: BORDER_COLOR, width: 1), // 테두리 추가
      ),
      elevation: 4, // 그림자 추가
      margin: EdgeInsets.symmetric(vertical: 10),
      child: child,
    );
  }
}
