import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:safety_signature_app/common/components/text_editor.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/enumeration/user_status_code.dart';
import 'package:safety_signature_app/user/model/user_model.dart';
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
    final state = ref.watch(userAuthProvider);
    if (state is! UserMinModel) {
      return Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          ElevatedButton(
              onPressed: () {
                // ref.read(userAuthProvider.notifier).userLogout();
              },
              child: Text(
                "로그인 후 이용 해주세요",
                style: defaultTextStyle,
              )),
        ],
      );
    }
    return Padding(
      padding: const EdgeInsets.all(24.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          _profile(model: state),
          Text("회원 활성 상태코드 : ${state.userStatusCode}" as String),
          Text("전자서명 ID : ${state.signatureDocId}" as String),
          Text("회원 고유 번호 : ${state.id}" as String),
          ElevatedButton(
              onPressed: () {
                ref.read(userAuthProvider.notifier).userLogout();
              },
              child: Text(
                "로그아웃",
                style: defaultTextStyle,
              )),
        ],
      ),
    );
  }

  Row _profile({required UserMinModel model}) {
    return Row(
      children: [
        ClipRRect(
          borderRadius: BorderRadius.circular(50),
          child: model.profileImageUri != null
              ? Image.network(
                  model.profileImageUri.toString(),
                  height: 70,
                  width: 70,
                  fit: BoxFit.cover,
                )
              : _noImageWidget(),
        ),
        SizedBox(
          width: 16,
        ),
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                model.name,
                style: defaultTextStyle,
              ),
              SizedBox(
                height: 8,
              ),
              Text(
                model.email,
                style: defaultTextStyle.copyWith(fontSize: 12),
              )
            ],
          ),
        ),
        SizedBox(
          width: 16,
        ),
        // ElevatedButton(
        //     onPressed: () {
        //       ref.read(userAuthProvider.notifier).userLogout();
        //     },
        //     style: ElevatedButton.styleFrom(
        //       elevation: 0,
        //     ),
        //     child: Text(
        //       "로그아웃",
        //       style: defaultTextStyle,
        //     )),
      ],
    );
  }

  Widget _noImageWidget() {
    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(50),
        color: BACK_GROUND_COLOR,
        border: Border.all(
          color: SECONDARY_COLOR.withOpacity(0.3),
          width: 1,
        ),
        boxShadow: [
          BoxShadow(
            color: SECONDARY_COLOR.withOpacity(0.5),
            spreadRadius: 1,
            blurRadius: 1,
            offset: Offset(1, 2),
          )
        ],
      ),
      width: 70,
      height: 70,
      child: Icon(Icons.person, size: 60, color: SECONDARY_COLOR),
    );
  }
}
