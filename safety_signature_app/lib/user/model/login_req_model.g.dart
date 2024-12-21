// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'login_req_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

LoginReqModel _$LoginReqModelFromJson(Map<String, dynamic> json) =>
    LoginReqModel(
      socialType: json['socialType'] as String?,
      name: json['name'] as String?,
      email: json['email'] as String?,
      mobile: json['mobile'] as String?,
    );

Map<String, dynamic> _$LoginReqModelToJson(LoginReqModel instance) =>
    <String, dynamic>{
      'socialType': instance.socialType,
      'name': instance.name,
      'email': instance.email,
      'mobile': instance.mobile,
    };
