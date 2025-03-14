import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:safety_signature_app/common/components/common_dialog.dart';
import 'package:safety_signature_app/common/components/pagination_list_view.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/const/data.dart';
import 'package:safety_signature_app/common/enumeration/user_status_code.dart';
import 'package:safety_signature_app/common/layout/default_layout.dart';
import 'package:safety_signature_app/common/model/permission_model.dart';
import 'package:safety_signature_app/common/provider/go_router.dart';
import 'package:safety_signature_app/common/provider/modal_controller_porivder.dart';
import 'package:safety_signature_app/common/provider/permission_provider.dart';
import 'package:safety_signature_app/user/model/user_model.dart';
import 'package:safety_signature_app/user/provider/user_auth_provider.dart';
import 'package:safety_signature_app/user/view/join_screen.dart';

class SplashScreen extends ConsumerStatefulWidget {
  static String get routeName => 'splash';
  final PermissionBase? permission;
  SplashScreen({this.permission});

  @override
  ConsumerState<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends ConsumerState<SplashScreen>
    with TickerProviderStateMixin {
  // late AnimationController controller;
  @override
  void initState() {
    // controller = AnimationController(
    //   vsync: this,
    //   duration: Duration(seconds: 2),
    // )..addListener(() {
    //     setState(() {});
    //   });
    // controller.forward();
    super.initState();
  }

  @override
  void dispose() {
    // controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final permission = ref.watch(permissionProvider);
    final state = ref.watch(userAuthProvider);
    final isPopUp = ref.watch(modalControllerProvider);
    if (state is UserMinModel &&
        UserStatusCode.getByCode(state.userStatusCode) ==
            UserStatusCode.PENDING) {
      ref.read(modalControllerProvider.notifier).isPopUp(visibility: true);
    }
    if (state is UserMinModel &&
        isPopUp &&
        (ModalRoute.of(context)?.isCurrent ?? false)) {
      WidgetsBinding.instance.addPostFrameCallback((_) {
        if (state is UserMinModel &&
            UserStatusCode.getByCode(state.userStatusCode) ==
                UserStatusCode.PENDING) {
          ref.read(modalControllerProvider.notifier).isPopUp(visibility: false);
          commonDialog(
            context: context,
            title: "회원가입 안내",
            content: Center(
                child: Text(
              "전자 서명 정보 등록 후 간편가입 완료됩니다.",
              style: defaultTextStyle,
            )),
            onConfirm: () async {
              final result = await context.pushNamed(JoinScreen.routeName);
              if (result == null) {
                await ref.watch(userAuthProvider.notifier).userLogout();
              }
            },
          );
        }
      });
    }

    return DefaultLayout(
      backgroundColor: BACK_GROUND_COLOR,
      child: SizedBox(
        width: MediaQuery.of(context).size.width,
        child: Stack(
          alignment: Alignment.center,
          children: [
            Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Text(
                    ip,
                    style: defaultTextStyle,
                  ),
                  Container(
                    height: 80,
                    child: Text(
                      "안전싸인",
                      style: TextStyle(
                        color: TEXT_COLOR,
                        fontWeight: FontWeight.bold,
                        fontSize: 32,
                      ),
                    ),
                  ),
                ]),
            if (permission is PermissionDenied ||
                permission is PermissionRestricted ||
                permission is PermissionPermanentlyDenied)
              Container(
                width: double.infinity,
                height: double.infinity,
                color: BACK_GROUND_COLOR.withOpacity(0.5),
                child: Center(
                  child: Container(
                    width: MediaQuery.of(context).size.width / 1.5,
                    height: 150,
                    decoration: BoxDecoration(
                        color: BACK_GROUND_COLOR,
                        borderRadius: BorderRadius.circular(10),
                        boxShadow: [
                          BoxShadow(
                            color: SECONDARY_COLOR.withOpacity(0.7),
                            blurRadius: 5.0,
                            spreadRadius: 5.0,
                            offset: const Offset(0, 4),
                          )
                        ]
                        // border: Border.all(color: TEXT_PRIMARY_COLOR),
                        ),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Text(
                          "안전싸인",
                          style: defaultTextStyle.copyWith(
                              fontSize: 20, fontWeight: FontWeight.w700),
                        ),
                        SizedBox(
                          height: 10,
                        ),
                        Text(
                          "접근 권한 허용 부탁드립니다.",
                          style: defaultTextStyle.copyWith(
                              fontWeight: FontWeight.w700),
                        ),
                        SizedBox(
                          height: 10,
                        ),
                        ElevatedButton(
                          onPressed: () {
                            openAppSettings();
                          },
                          child: Text("확인"),
                          style: ElevatedButton.styleFrom(
                              foregroundColor: TEXT_COLOR,
                              backgroundColor: BACK_GROUND_COLOR),
                        )
                      ],
                    ),
                  ),
                ),
              ),
          ],
        ),
      ),
    );
  }
}
