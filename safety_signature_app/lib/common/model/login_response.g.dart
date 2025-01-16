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

LoginFailedResponse _$LoginFailedResponseFromJson(Map<String, dynamic> json) =>
    LoginFailedResponse(
      message: json['message'] as String,
      httpStatus: json['httpStatus'] as String,
    );

Map<String, dynamic> _$LoginFailedResponseToJson(
        LoginFailedResponse instance) =>
    <String, dynamic>{
      'message': instance.message,
      'httpStatus': instance.httpStatus,
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
