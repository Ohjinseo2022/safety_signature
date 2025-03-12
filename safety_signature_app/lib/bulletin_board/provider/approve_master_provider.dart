import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:safety_signature_app/bulletin_board/model/approve_signature_model.dart';
import 'package:safety_signature_app/bulletin_board/provider/bulletin_board_provider.dart';
import 'package:safety_signature_app/bulletin_board/repository/approve_master_repository.dart';
import 'package:safety_signature_app/bulletin_board/repository/bulletin_board_repository.dart';
import 'package:safety_signature_app/common/model/cursor_pagination_model.dart';

final approveMasterProvider = StateNotifierProvider<ApproveMasterStateNotifier,
    ApproveSignatureModelBase>((ref) {
  final approveMasterRepository = ref.watch(approveMasterRepositoryProvider);

  return ApproveMasterStateNotifier(
    approveMasterRepository: approveMasterRepository,
  );
});

class ApproveMasterStateNotifier
    extends StateNotifier<ApproveSignatureModelBase> {
  final ApproveMasterRepository approveMasterRepository;

  ApproveMasterStateNotifier({
    required this.approveMasterRepository,
  }) : super(ApproveSignatureModelBase());

  Future<void> approveSignature({required String bulletinBoardId}) async {
    final isLoading = state is ApproveSignatureLoading;
    if (isLoading) {
      // print("로딩상태니 ?");
      return;
    }
    try {
      state = ApproveSignatureLoading();
      // print("bulletinBoardId! $bulletinBoardId");
      final approveResponse =
          await approveMasterRepository.postCompletedSignature(
              approveSignatureRequestModel: ApproveSignatureRequestModel(
                  bulletinBoardId: bulletinBoardId));
      state = approveResponse;
    } catch (e) {
      state = ApproveSignatureMessageModel(message: '결재 실패 잠시 후 다시 시도해 주세요.');
    }
  }
}
