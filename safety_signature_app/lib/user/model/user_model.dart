import 'package:json_annotation/json_annotation.dart';

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

  UserMinModel({
    required this.id,
    required this.name,
    this.profileImageUri,
    required this.email,
  });
  factory UserMinModel.fromJson(Map<String, dynamic> json) =>
      _$UserMinModelFromJson(json);
}

@JsonSerializable()
class UserSummaryModel extends UserMinModel {
  final String? mobile;
  UserSummaryModel(
      {required super.id,
      required super.name,
      required super.email,
      super.profileImageUri,
      this.mobile});
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
    super.profileImageUri,
    super.mobile,
    this.googleSignIn = false,
    this.kakaoSignIn = false,
    this.naverSignIn = false,
  });
  factory UserDetailModel.fromJson(Map<String, dynamic> json) =>
      _$UserDetailModelFromJson(json);
}

@JsonSerializable()
class UserHiddenModel extends UserDetailModel {
  final String userPassword;
  UserHiddenModel({
    required super.id,
    required super.name,
    required super.email,
    super.profileImageUri,
    super.mobile,
    super.googleSignIn,
    super.kakaoSignIn,
    super.naverSignIn,
    required this.userPassword,
  });
  factory UserHiddenModel.fromJson(Map<String, dynamic> json) =>
      _$UserHiddenModelFromJson(json);
}
