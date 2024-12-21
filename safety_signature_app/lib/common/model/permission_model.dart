import 'package:json_annotation/json_annotation.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:safety_signature_app/common/enumeration/permission_type_code.dart';
part 'permission_model.g.dart';

class PermissionBase {}

class PermissionLoading extends PermissionBase {} // 대기중

class PermissionGranted extends PermissionBase {} // 허가

class PermissionDenied extends PermissionBase {} //거부

class PermissionRestricted extends PermissionBase {} //영구거부 ios

class PermissionLimited extends PermissionBase {} //제한적 인

@JsonSerializable()
class PermissionPermanentlyDenied extends PermissionBase {
  final PermissionTypeCode permission;
  PermissionPermanentlyDenied({required this.permission});
  factory PermissionPermanentlyDenied.fromJson(Map<String, dynamic> json) =>
      _$PermissionPermanentlyDeniedFromJson(json);
} // 영구거부

class PermissionProvisional extends PermissionBase {} // 조용한 알림 처리
