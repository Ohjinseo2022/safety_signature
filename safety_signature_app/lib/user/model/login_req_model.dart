import 'package:json_annotation/json_annotation.dart';
part 'login_req_model.g.dart';

@JsonSerializable()
class LoginReqModel {
  final String? socialType;
  final String? name;
  final String? email;
  final String? mobile;

  LoginReqModel({this.socialType, this.name, this.email, this.mobile});
  factory LoginReqModel.fromJson(Map<String, dynamic> json) =>
      _$LoginReqModelFromJson(json);

  Map<String, dynamic> toJson() => _$LoginReqModelToJson(this);
}
