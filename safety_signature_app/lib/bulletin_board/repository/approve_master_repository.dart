import 'package:dio/dio.dart' hide Headers;
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:retrofit/http.dart';
import 'package:safety_signature_app/bulletin_board/model/approve_signature_model.dart';
import 'package:safety_signature_app/common/const/data.dart';
import 'package:safety_signature_app/common/dio/dio.dart';

part 'approve_master_repository.g.dart';

final approveMasterRepositoryProvider =
    Provider<ApproveMasterRepository>((ref) {
  final dio = ref.watch(dioProvider);
  final repository = ApproveMasterRepository(
    dio,
    baseUrl: "$ip$baseUrl/approve",
  );
  return repository;
});

@RestApi()
abstract class ApproveMasterRepository {
  factory ApproveMasterRepository(Dio dio, {String baseUrl}) =
      _ApproveMasterRepository;

  @POST('/completed-signature')
  @Headers({'accessToken': 'true'})
  Future<ApproveSignatureMessageModel> postCompletedSignature(
      {@Body()
      required ApproveSignatureRequestModel approveSignatureRequestModel});
}
