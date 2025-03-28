// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UserMinModel _$UserMinModelFromJson(Map<String, dynamic> json) => UserMinModel(
      id: json['id'] as String,
      userName: json['userName'] as String?,
      profileImageUri: json['profileImageUri'] as String?,
      email: json['email'] as String,
      signatureDocId: json['signatureDocId'] as String?,
      mobile: json['mobile'] as String?,
      userStatusCode: json['userStatusCode'] as String,
      userTypeCode: UserTypeCode.getByCode(json['userTypeCode'] as String),
    );

Map<String, dynamic> _$UserMinModelToJson(UserMinModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'userName': instance.userName,
      'profileImageUri': instance.profileImageUri,
      'email': instance.email,
      'mobile': instance.mobile,
      'signatureDocId': instance.signatureDocId,
      'userStatusCode': instance.userStatusCode,
      'userTypeCode': _$UserTypeCodeEnumMap[instance.userTypeCode]!,
    };

const _$UserTypeCodeEnumMap = {
  UserTypeCode.MASTER_ADMINISTRATOR: 'MASTER_ADMINISTRATOR',
  UserTypeCode.CORPORATE_MANAGER: 'CORPORATE_MANAGER',
  UserTypeCode.GENERAL_MEMBER: 'GENERAL_MEMBER',
  UserTypeCode.UNDEFINED: 'UNDEFINED',
};

UserSummaryModel _$UserSummaryModelFromJson(Map<String, dynamic> json) =>
    UserSummaryModel(
      id: json['id'] as String,
      userName: json['userName'] as String?,
      email: json['email'] as String,
      userStatusCode: json['userStatusCode'] as String,
      profileImageUri: json['profileImageUri'] as String?,
      signatureDocId: json['signatureDocId'] as String?,
      mobile: json['mobile'] as String?,
      userTypeCode: UserTypeCode.getByCode(json['userTypeCode'] as String),
    );

Map<String, dynamic> _$UserSummaryModelToJson(UserSummaryModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'userName': instance.userName,
      'profileImageUri': instance.profileImageUri,
      'email': instance.email,
      'mobile': instance.mobile,
      'signatureDocId': instance.signatureDocId,
      'userStatusCode': instance.userStatusCode,
      'userTypeCode': _$UserTypeCodeEnumMap[instance.userTypeCode]!,
    };

UserDetailModel _$UserDetailModelFromJson(Map<String, dynamic> json) =>
    UserDetailModel(
      id: json['id'] as String,
      userName: json['userName'] as String?,
      email: json['email'] as String,
      userStatusCode: json['userStatusCode'] as String,
      userTypeCode: UserTypeCode.getByCode(json['userTypeCode'] as String),
      profileImageUri: json['profileImageUri'] as String?,
      mobile: json['mobile'] as String?,
      signatureDocId: json['signatureDocId'] as String?,
      googleSignIn: json['googleSignIn'] as bool? ?? false,
      kakaoSignIn: json['kakaoSignIn'] as bool? ?? false,
      naverSignIn: json['naverSignIn'] as bool? ?? false,
    );

Map<String, dynamic> _$UserDetailModelToJson(UserDetailModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'userName': instance.userName,
      'profileImageUri': instance.profileImageUri,
      'email': instance.email,
      'mobile': instance.mobile,
      'signatureDocId': instance.signatureDocId,
      'userStatusCode': instance.userStatusCode,
      'userTypeCode': _$UserTypeCodeEnumMap[instance.userTypeCode]!,
      'googleSignIn': instance.googleSignIn,
      'kakaoSignIn': instance.kakaoSignIn,
      'naverSignIn': instance.naverSignIn,
    };

UserHiddenModel _$UserHiddenModelFromJson(Map<String, dynamic> json) =>
    UserHiddenModel(
      id: json['id'] as String,
      userName: json['userName'] as String?,
      email: json['email'] as String,
      userStatusCode: json['userStatusCode'] as String,
      userTypeCode: UserTypeCode.getByCode(json['userTypeCode'] as String),
      profileImageUri: json['profileImageUri'] as String?,
      signatureDocId: json['signatureDocId'] as String?,
      mobile: json['mobile'] as String?,
      googleSignIn: json['googleSignIn'] as bool? ?? false,
      kakaoSignIn: json['kakaoSignIn'] as bool? ?? false,
      naverSignIn: json['naverSignIn'] as bool? ?? false,
      password: json['password'] as String,
    );

Map<String, dynamic> _$UserHiddenModelToJson(UserHiddenModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'userName': instance.userName,
      'profileImageUri': instance.profileImageUri,
      'email': instance.email,
      'mobile': instance.mobile,
      'signatureDocId': instance.signatureDocId,
      'userStatusCode': instance.userStatusCode,
      'userTypeCode': _$UserTypeCodeEnumMap[instance.userTypeCode]!,
      'googleSignIn': instance.googleSignIn,
      'kakaoSignIn': instance.kakaoSignIn,
      'naverSignIn': instance.naverSignIn,
      'password': instance.password,
    };
