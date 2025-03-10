import 'package:dio/dio.dart' hide Headers;
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:retrofit/http.dart';
import 'package:safety_signature_app/common/const/data.dart';
import 'package:safety_signature_app/common/dio/dio.dart';
import 'package:safety_signature_app/common/model/login_response.dart';
import 'package:safety_signature_app/user/model/login_req_model.dart';

part 'auth_repository.g.dart';

final authRepositoryProvider = Provider<AuthRepository>((ref) {
  final dio = ref.watch(dioProvider);
  return AuthRepository(dio, baseUrl: '$ip$baseUrl');
});

@RestApi()
abstract class AuthRepository {
  factory AuthRepository(Dio dio, {String baseUrl}) = _AuthRepository;

  //{String? email, String? password}
  @POST('/login/social')
  @Headers({'accessToken': 'true'})
  Future<LoginResponse> login({@Body() required LoginReqModel loginReqDTO});

  @POST('/login/normal')
  // @Headers({'accessToken': 'true'})
  Future<LoginResponseBase?> normalLogin({
    @Body() required LoginNormalReqModel loginNormalReqModel,
  });
}
