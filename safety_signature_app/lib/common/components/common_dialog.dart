import 'package:flutter/material.dart';
import 'package:safety_signature_app/common/const/color.dart';

Future<void> commonDialog(
    {required BuildContext context,
    String? title,
    required Widget content,
    required VoidCallback onConfirm,
    VoidCallback? onCancel,
    bool barrierDismissible = false}) async {
  return showDialog<void>(
    context: context,
    barrierDismissible: barrierDismissible, // 다이얼로그 외부를 눌러도 닫히지 않도록 설정
    builder: (BuildContext context) {
      return AlertDialog(
        title: title == null ? null : Text(title),
        backgroundColor: BACK_GROUND_COLOR,
        content: SingleChildScrollView(
          child: ListBody(
            children: <Widget>[
              content,
            ],
          ),
        ),
        actions: [
          ElevatedButton(
            style: ElevatedButton.styleFrom(backgroundColor: PRIMARY_COLOR),
            child: Text(
              '확인',
              style: defaultTextStyle,
            ),
            onPressed: () {
              onConfirm();
              Navigator.of(context).pop();
            },
          ),
          if (onCancel != null) // onCancel이 전달된 경우만 '취소' 버튼 표시
            ElevatedButton(
              style: ElevatedButton.styleFrom(backgroundColor: PRIMARY_COLOR),
              child: Text(
                '취소',
                style: defaultTextStyle,
              ),
              onPressed: () {
                onCancel();
                Navigator.of(context).pop();
              },
            ),
        ],
      );
    },
  );
}
