import 'dart:io';

import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:safety_signature_app/common/enumeration/permission_type_code.dart';
import 'package:safety_signature_app/common/model/permission_model.dart';

final permissionProvider =
    StateNotifierProvider<PermissionProviderStateNotifier, PermissionBase?>(
        (ref) {
  return PermissionProviderStateNotifier(permissionTypes: [
    Permission.calendarFullAccess,
    Permission.notification,
    Permission.reminders,
  ]);
});

class PermissionProviderStateNotifier extends StateNotifier<PermissionBase?> {
  final List<Permission> permissionTypes;

  PermissionProviderStateNotifier({required this.permissionTypes})
      : super(PermissionLoading()) {
    initAppRequest();
  }
  //앱 초기 실행시 권한을 가지고 오는 상태
  Future<PermissionBase?> initAppRequest() async {
    final permission = await permissionTypes.request();

    /**
     * 1. 전부 허가
     * 2. 전부 거부
     * 3. 1개라도 거부가 있는 경우
     * 4. 1개라도 제한적인 허용이 있는경우
     * TODO: provisional 미리 알림 관련 로직 추가 필요함 지금은 머리가 안돌아가요.
     */
    //granted, denied, 공통
    //permanentlyDenied -- 영구히 거부 , 다시 요청을 하지 않는 상태
    // PermissionStatus.limited restricted , provisional - ios 전용
    final calendarFullAccess = permission[Permission.calendarFullAccess]!;
    final notification = permission[Permission.notification]!;
    final reminders = permission[Permission.reminders]!;
    // 전체 허용
    bool isGranted;
    if (Platform.isIOS) {
      isGranted = calendarFullAccess.isGranted ||
          calendarFullAccess.isLimited && notification.isGranted ||
          notification.isLimited && reminders.isGranted ||
          reminders.isLimited;
    } else {
      isGranted = calendarFullAccess.isGranted &&
          notification.isGranted &&
          reminders.isGranted;
    }
    if (isGranted) {
      state = PermissionGranted();
      return state;
    } else {
      print(permission);
      for (var key in permission.keys) {
        print("key : $key");
        if (permission[key]!.isPermanentlyDenied) {
          //영구적으로 거부 aos
          state = PermissionPermanentlyDenied(
              permission: PermissionTypeCode.getByCode(key.toString()));
          return state;
        }

        if (permission[key]!.isRestricted) {
          //영구적으로 거부 ios
          state = PermissionPermanentlyDenied(
              permission: PermissionTypeCode.getByCode(key.toString()));
          return state;
        }
      }
      print("state : $state");
      state = PermissionDenied();
      return state;
    }
  }
}
