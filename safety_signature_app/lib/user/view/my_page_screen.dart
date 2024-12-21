import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:safety_signature_app/common/components/text_editor.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/user/provider/user_auth_provider.dart';

class MyPageScreen extends ConsumerStatefulWidget {
  static String get routeName => 'myPageScreen';
  const MyPageScreen({super.key});

  @override
  ConsumerState<MyPageScreen> createState() => _MyPageScreenState();
}

class _MyPageScreenState extends ConsumerState<MyPageScreen> {
  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        ElevatedButton(
            onPressed: () {
              ref.read(userAuthProvider.notifier).userLogout();
            },
            child: Text(
              "로그아웃",
              style: defaultTextStyle,
            )),
        TextEditor()
      ],
    );
  }
}
