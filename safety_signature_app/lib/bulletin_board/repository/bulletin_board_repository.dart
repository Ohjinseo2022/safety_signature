import 'package:dio/dio.dart' hide Headers;
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:retrofit/http.dart';
import 'package:safety_signature_app/bulletin_board/model/bulletin_board_model.dart';
import 'package:safety_signature_app/common/const/data.dart';
import 'package:safety_signature_app/common/dio/dio.dart';
import 'package:safety_signature_app/common/model/cursor_pagination_model.dart';
import 'package:safety_signature_app/common/model/pagination_params.dart';
import 'package:safety_signature_app/common/repository/base_pagination_repository.dart';

part 'bulletin_board_repository.g.dart';

final bulletinBoardRepositoryProvider =
    Provider<BulletinBoardRepository>((ref) {
  final dio = ref.watch(dioProvider);
  final repository = BulletinBoardRepository(
    dio,
    baseUrl: "http://$ip$baseUrl/bulletin-board/registration",
  );
  return repository;
});

@RestApi()
abstract class BulletinBoardRepository
    implements IBasePaginationRepository<BulletinBoardModel> {
  factory BulletinBoardRepository(Dio dio, {String baseUrl}) =
      _BulletinBoardRepository;

  @override
  @GET('/list-for-user')
  @Headers({
    'accessToken': 'true',
  })
  Future<CursorPagination<BulletinBoardModel>> paginate(
      {@Queries()
      PaginationParams? paginationParams = const PaginationParams()});
}
