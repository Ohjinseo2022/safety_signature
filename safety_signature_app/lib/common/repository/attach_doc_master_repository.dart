import 'package:dio/dio.dart' hide Headers;
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:retrofit/dio.dart';
import 'package:retrofit/http.dart';
import 'package:safety_signature_app/common/const/data.dart';
import 'package:safety_signature_app/common/dio/dio.dart';

part 'attach_doc_master_repository.g.dart';

final attachDocMasterRepositoryProvider =
    Provider<AttachDocMasterRepository>((ref) {
  final dio = ref.watch(dioProvider);
  final repository = AttachDocMasterRepository(
    dio,
    baseUrl: "$ip$baseUrl/attach",
  );
  return repository;
});

@RestApi()
abstract class AttachDocMasterRepository {
  factory AttachDocMasterRepository(Dio dio, {String baseUrl}) =
      _AttachDocMasterRepository;

  @GET('/download/{id}')
  @Headers({"Accept": "application/octet-stream"})
  Future<List<int>> downloadFile(
      @Path() String id, @DioOptions() Options options);
}
