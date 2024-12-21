// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UserMinModel _$UserMinModelFromJson(Map<String, dynamic> json) => UserMinModel(
      id: json['id'] as String,
      name: json['name'] as String,
      profileImageUri: json['profileImageUri'] as String?,
      email: json['email'] as String,
    );

Map<String, dynamic> _$UserMinModelToJson(UserMinModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'profileImageUri': instance.profileImageUri,
      'email': instance.email,
    };

UserSummaryModel _$UserSummaryModelFromJson(Map<String, dynamic> json) =>
    UserSummaryModel(
      id: json['id'] as String,
      name: json['name'] as String,
      email: json['email'] as String,
      profileImageUri: json['profileImageUri'] as String?,
      mobile: json['mobile'] as String?,
    );

Map<String, dynamic> _$UserSummaryModelToJson(UserSummaryModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'profileImageUri': instance.profileImageUri,
      'email': instance.email,
      'mobile': instance.mobile,
    };

UserDetailModel _$UserDetailModelFromJson(Map<String, dynamic> json) =>
    UserDetailModel(
      id: json['id'] as String,
      name: json['name'] as String,
      email: json['email'] as String,
      profileImageUri: json['profileImageUri'] as String?,
      mobile: json['mobile'] as String?,
      googleSignIn: json['googleSignIn'] as bool? ?? false,
      kakaoSignIn: json['kakaoSignIn'] as bool? ?? false,
      naverSignIn: json['naverSignIn'] as bool? ?? false,
    );

Map<String, dynamic> _$UserDetailModelToJson(UserDetailModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'profileImageUri': instance.profileImageUri,
      'email': instance.email,
      'mobile': instance.mobile,
      'googleSignIn': instance.googleSignIn,
      'kakaoSignIn': instance.kakaoSignIn,
      'naverSignIn': instance.naverSignIn,
    };

UserHiddenModel _$UserHiddenModelFromJson(Map<String, dynamic> json) =>
    UserHiddenModel(
      id: json['id'] as String,
      name: json['name'] as String,
      email: json['email'] as String,
      profileImageUri: json['profileImageUri'] as String?,
      mobile: json['mobile'] as String?,
      googleSignIn: json['googleSignIn'] as bool? ?? false,
      kakaoSignIn: json['kakaoSignIn'] as bool? ?? false,
      naverSignIn: json['naverSignIn'] as bool? ?? false,
      userPassword: json['userPassword'] as String,
    );

Map<String, dynamic> _$UserHiddenModelToJson(UserHiddenModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'profileImageUri': instance.profileImageUri,
      'email': instance.email,
      'mobile': instance.mobile,
      'googleSignIn': instance.googleSignIn,
      'kakaoSignIn': instance.kakaoSignIn,
      'naverSignIn': instance.naverSignIn,
      'userPassword': instance.userPassword,
    };
