import 'package:encrypt/encrypt.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:safety_signature_app/bulletin_board/model/bulletin_board_model.dart';
import 'package:safety_signature_app/bulletin_board/repository/bulletin_board_repository.dart';
import 'package:safety_signature_app/common/model/cursor_pagination_model.dart';
import 'package:safety_signature_app/common/provider/pagination_provider.dart';
import 'package:collection/collection.dart';

final bulletinBoardDetailProvider =
    Provider.family<BulletinBoardModel?, String>((ref, id) {
  final state = ref.watch(bulletinBoardProvider);
  if (state is! CursorPagination) {
    return null;
  }
  return state.data.firstWhereOrNull((e) => e.id == id);
});

final bulletinBoardProvider =
    StateNotifierProvider<BulletinBoardStateNotifier, CursorPaginationBase>(
        (ref) {
  final repository = ref.watch(bulletinBoardRepositoryProvider);
  final notifier = BulletinBoardStateNotifier(repository: repository);
  return notifier;
});

class BulletinBoardStateNotifier
    extends PaginationProvider<BulletinBoardModel, BulletinBoardRepository> {
  BulletinBoardStateNotifier({
    required super.repository,
  });
/**
 * TODO: 상세 조회 로직 개발, 리스트에 같은 id를 가진 요소에 디테일 내용 추가 필요함
 */
  void getDetail({required String id}) async {
    //아직 아무 데이터도 없는 상태라면 데이터 가져오는 시도.
    if (state is! CursorPagination) {
      await paginate();
    }
    /**
     * 데이터를 가져온 상태인데도 CursorPagination 상태가 아니라면 (게시글 자체가 없거나 오류가 난상황)
     * return
     */
    if (state is! CursorPagination) {
      return;
    }
    final pState = state as CursorPagination;

    final response = await repository.getBulletinBoardDetail(id: id);
    if (pState.data.where((e) => e.id == id).isEmpty) {
      state = pState
          .copyWith(data: <BulletinBoardModel>[...pState.data, response.data]);
    } else {
      state = pState.copyWith(
          data: pState.data
              .map<BulletinBoardModel>((e) => e.id == id ? response.data : e)
              .toList());
    }
  }
}
