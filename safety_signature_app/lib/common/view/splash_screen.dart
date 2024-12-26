import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/layout/default_layout.dart';
import 'package:safety_signature_app/common/model/permission_model.dart';
import 'package:safety_signature_app/common/provider/permission_provider.dart';

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
                  Container(
                    height: 80,
                    child: Text(
                      "안전싸인",
                      style: TextStyle(
                        color: SELECT_TEXT_COLOR,
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
                color: UNSELECT_TEXT_COLOR.withOpacity(0.5),
                child: Center(
                  child: Container(
                    width: MediaQuery.of(context).size.width / 1.5,
                    height: 150,
                    decoration: BoxDecoration(
                        color: BACK_GROUND_COLOR,
                        borderRadius: BorderRadius.circular(10),
                        boxShadow: [
                          BoxShadow(
                            color: UNSELECT_TEXT_COLOR.withOpacity(0.7),
                            blurRadius: 5.0,
                            spreadRadius: 5.0,
                            offset: const Offset(0, 4),
                          )
                        ]
                        // border: Border.all(color: SELECT_TEXT_COLOR),
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
                              foregroundColor: SELECT_TEXT_COLOR,
                              backgroundColor: BACK_GROUND_COLOR_LIGHT),
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
