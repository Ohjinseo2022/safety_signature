import 'package:dio/dio.dart' hide Headers;
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:retrofit/http.dart';
import 'package:safety_signature_app/common/const/data.dart';
import 'package:safety_signature_app/common/dio/dio.dart';
import 'package:safety_signature_app/user/model/post_join_body.dart';
import 'package:safety_signature_app/user/model/user_model.dart';

part 'user_master_repository.g.dart';

final userMasterRepositoryProvider = Provider<UserMasterRepository>((ref) {
  final dio = ref.watch(dioProvider);
  return UserMasterRepository(dio, baseUrl: 'http://$ip$baseUrl/user');
});

@RestApi()
abstract class UserMasterRepository {
  factory UserMasterRepository(Dio dio, {String baseUrl}) =
      _UserMasterRepository;

  @GET('/profile')
  @Headers({'accessToken': 'true'})
  Future<UserMinModel?> userProfile();

  @GET('/logout')
  @Headers({'accessToken': 'true'})
  Future<void> userLogout();

  @POST('/join')
  @Headers({'accessToken': 'true'})
  Future<UserMinModel?> userJoin({
    @Body() required PostJoinBody postJoinBody,
  });
}
