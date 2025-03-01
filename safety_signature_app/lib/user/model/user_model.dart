import 'package:json_annotation/json_annotation.dart';
import 'package:safety_signature_app/common/enumeration/user_status_code.dart';
import 'package:safety_signature_app/common/enumeration/user_type_code.dart';

part 'user_model.g.dart';

abstract class UserModelBase {}

class UserModelError extends UserModelBase {
  final String message;
  UserModelError({required this.message});
}

class UserModelLoading extends UserModelBase {}

class UserModelGuest extends UserModelBase {}

@JsonSerializable()
class UserMinModel extends UserModelBase {
  final String id;
  final String name;
  // @JsonKey(
  //   fromJson: DataUtils.pathToUrl,
  // )
  final String? profileImageUri;
  final String email;
  final String? mobile;
  final String? signatureDocId;

  final String userStatusCode;
  @JsonKey(fromJson: UserTypeCode.getByCode)
  final UserTypeCode userTypeCode;
  UserMinModel(
      {required this.id,
      required this.name,
      this.profileImageUri,
      required this.email,
      this.signatureDocId,
      this.mobile,
      required this.userStatusCode,
      required this.userTypeCode});
  factory UserMinModel.fromJson(Map<String, dynamic> json) =>
      _$UserMinModelFromJson(json);
}

@JsonSerializable()
class UserSummaryModel extends UserMinModel {
  UserSummaryModel(
      {required super.id,
      required super.name,
      required super.email,
      required super.userStatusCode,
      super.profileImageUri,
      super.signatureDocId,
      super.mobile,
      required super.userTypeCode});
  factory UserSummaryModel.fromJson(Map<String, dynamic> json) =>
      _$UserSummaryModelFromJson(json);
}

@JsonSerializable()
class UserDetailModel extends UserSummaryModel {
  final bool? googleSignIn;
  final bool? kakaoSignIn;
  final bool? naverSignIn;

  UserDetailModel({
    required super.id,
    required super.name,
    required super.email,
    required super.userStatusCode,
    required super.userTypeCode,
    super.profileImageUri,
    super.mobile,
    super.signatureDocId,
    this.googleSignIn = false,
    this.kakaoSignIn = false,
    this.naverSignIn = false,
  });
  factory UserDetailModel.fromJson(Map<String, dynamic> json) =>
      _$UserDetailModelFromJson(json);
}

@JsonSerializable()
class UserHiddenModel extends UserDetailModel {
  final String password;
  UserHiddenModel({
    required super.id,
    required super.name,
    required super.email,
    required super.userStatusCode,
    required super.userTypeCode,
    super.profileImageUri,
    super.signatureDocId,
    super.mobile,
    super.googleSignIn,
    super.kakaoSignIn,
    super.naverSignIn,
    required this.password,
  });
  factory UserHiddenModel.fromJson(Map<String, dynamic> json) =>
      _$UserHiddenModelFromJson(json);
}
