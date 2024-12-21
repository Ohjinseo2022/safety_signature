import 'package:json_annotation/json_annotation.dart';
part 'login_response.g.dart';

@JsonSerializable()
//TODO: 테스트 완료하고 UserModelBase 제거
class LoginResponse {
  final String refreshToken;
  final String accessToken;

  LoginResponse({required this.refreshToken, required this.accessToken});

  factory LoginResponse.fromJson(Map<String, dynamic> json) =>
      _$LoginResponseFromJson(json);
}
