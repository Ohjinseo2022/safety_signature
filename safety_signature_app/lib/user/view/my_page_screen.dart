import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:safety_signature_app/common/components/text_editor.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/const/data.dart';
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
      if (state is! UserMinModel) {
        return Center(
          child: ElevatedButton(
            onPressed: () {},
            child: Text(
              "로그인 후 이용 해주세요",
              style: TextStyle(fontSize: 16),
            ),
          ),
        );
      }
    }
    return SingleChildScrollView(
      padding: EdgeInsets.all(20),
      child: Column(
        children: [
          _buildProfileCard(state),
          SizedBox(height: 20),
          _buildInfoCard(state),
          SizedBox(height: 20),
          _buildMenuList(context, ref),
        ],
      ),
    );
  }

  /// ✅ 프로필 카드 (사용자 정보)
  Widget _buildProfileCard(UserMinModel model) {
    return Container(
      padding: EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: PRIMARY_COLOR,
        borderRadius: BorderRadius.circular(12),
        boxShadow: [
          BoxShadow(color: Colors.black26, blurRadius: 5, offset: Offset(0, 2))
        ],
      ),
      child: Row(
        children: [
          ClipRRect(
            borderRadius: BorderRadius.circular(50),
            child: model.profileImageUri != null
                ? Image.network(
                    model.profileImageUri!,
                    height: 70,
                    width: 70,
                    fit: BoxFit.cover,
                  )
                : Icon(Icons.person, size: 70, color: SECONDARY_COLOR),
          ),
          SizedBox(width: 16),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  model.userName ?? '',
                  style: TextStyle(
                    color: SECONDARY_COLOR,
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                SizedBox(height: 4),
                Text(
                  model.email,
                  style: TextStyle(color: SECONDARY_COLOR.withOpacity(0.8)),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  /// ✅ 사용자 정보 카드 (회원 ID, 상태 등)
  Widget _buildInfoCard(UserMinModel model) {
    return Card(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      elevation: 4,
      child: Padding(
        padding: EdgeInsets.all(16),
        child: Column(
          children: [
            _infoRow("회원 유형", model.userTypeCode.displayName),
            Divider(),
            _infoRow("회원 고유 번호", model.id),
            Divider(),
            _infoRow("전자서명 정보", model.signatureDocId ?? "N/A", isImg: true),
          ],
        ),
      ),
    );
  }

  /// ✅ 설정 & 로그아웃 메뉴
  Widget _buildMenuList(BuildContext context, WidgetRef ref) {
    return Column(
      children: [
        ListTile(
          shape:
              RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
          leading: Icon(Icons.settings, color: PRIMARY_COLOR),
          title: Text(
            "설정",
            style: defaultTextStyle,
          ),
          onTap: () {},
          tileColor: CARD_COLOR,
        ),
        SizedBox(
          height: 3,
        ),
        ListTile(
          shape:
              RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
          leading: Icon(Icons.lock, color: PRIMARY_COLOR),
          title: Text(
            "비밀번호 변경",
            style: defaultTextStyle,
          ),
          onTap: () {},
          tileColor: CARD_COLOR,
        ),
        SizedBox(
          height: 3,
        ),
        ListTile(
          shape:
              RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
          leading: Icon(Icons.logout, color: Colors.red),
          title: Text(
            "로그아웃",
            style: defaultTextStyle,
          ),
          onTap: () {
            ref.read(userAuthProvider.notifier).userLogout();
          },
          tileColor: CARD_COLOR,
        ),
      ],
    );
  }

  /// ✅ 정보 표시용 위젯
  Widget _infoRow(String label, String value, {bool isImg = false}) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Text(label, style: TextStyle(fontWeight: FontWeight.bold)),
        isImg
            ? Image.network(
                '$ip$baseUrl/attach/download/$value',
                height: 50,
                width: 100,
                fit: BoxFit.cover,
              )
            : Text(value)
      ],
    );
  }
}
