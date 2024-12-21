// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'permission_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

PermissionPermanentlyDenied _$PermissionPermanentlyDeniedFromJson(
        Map<String, dynamic> json) =>
    PermissionPermanentlyDenied(
      permission: $enumDecode(_$PermissionTypeCodeEnumMap, json['permission']),
    );

Map<String, dynamic> _$PermissionPermanentlyDeniedToJson(
        PermissionPermanentlyDenied instance) =>
    <String, dynamic>{
      'permission': _$PermissionTypeCodeEnumMap[instance.permission]!,
    };

const _$PermissionTypeCodeEnumMap = {
  PermissionTypeCode.CALENDARFULLACCESS: 'CALENDARFULLACCESS',
  PermissionTypeCode.NOTIFICATION: 'NOTIFICATION',
  PermissionTypeCode.REMINDERS: 'REMINDERS',
  PermissionTypeCode.UNDEFINED: 'UNDEFINED',
};
