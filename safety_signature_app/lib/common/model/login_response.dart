import 'package:json_annotation/json_annotation.dart';
import 'package:safety_signature_app/common/enumeration/user_status_code.dart';
part 'login_response.g.dart';

abstract class LoginResponseBase {
  // JSON 데이터에 따라 적절한 서브클래스를 반환
  factory LoginResponseBase.fromJson(Map<String, dynamic> json) {
    if (json.containsKey('refreshToken') && json.containsKey('accessToken')) {
      return LoginResponse.fromJson(json);
    } else if (json.containsKey('userStatusCode') &&
        json.containsKey('message')) {
      return JoinFailedResponse.fromJson(json);
    } else {
      throw Exception("Unknown response type");
    }
  }
}

@JsonSerializable()
class LoginResponse implements LoginResponseBase {
  final String refreshToken;
  final String accessToken;

  LoginResponse({required this.refreshToken, required this.accessToken});

  factory LoginResponse.fromJson(Map<String, dynamic> json) =>
      _$LoginResponseFromJson(json);
}

@JsonSerializable()
class JoinFailedResponse implements LoginResponseBase {
  final String userStatusCode;
  final String message;

  JoinFailedResponse({required this.userStatusCode, required this.message});

  factory JoinFailedResponse.fromJson(Map<String, dynamic> json) =>
      _$JoinFailedResponseFromJson(json);
}
