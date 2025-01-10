import 'package:flutter/material.dart';

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
        content: SingleChildScrollView(
          child: ListBody(
            children: <Widget>[
              content,
            ],
          ),
        ),
        actions: [
          TextButton(
            child: Text('확인'),
            onPressed: () {
              onConfirm();
              Navigator.of(context).pop();
            },
          ),
          if (onCancel != null) // onCancel이 전달된 경우만 '취소' 버튼 표시
            TextButton(
              child: Text('취소'),
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
