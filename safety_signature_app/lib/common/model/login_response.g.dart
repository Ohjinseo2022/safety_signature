// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'login_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

LoginResponse _$LoginResponseFromJson(Map<String, dynamic> json) =>
    LoginResponse(
      refreshToken: json['refreshToken'] as String,
      accessToken: json['accessToken'] as String,
    );

Map<String, dynamic> _$LoginResponseToJson(LoginResponse instance) =>
    <String, dynamic>{
      'refreshToken': instance.refreshToken,
      'accessToken': instance.accessToken,
    };

JoinFailedResponse _$JoinFailedResponseFromJson(Map<String, dynamic> json) =>
    JoinFailedResponse(
      userStatusCode: json['userStatusCode'] as String,
      message: json['message'] as String,
    );

Map<String, dynamic> _$JoinFailedResponseToJson(JoinFailedResponse instance) =>
    <String, dynamic>{
      'userStatusCode': instance.userStatusCode,
      'message': instance.message,
    };
