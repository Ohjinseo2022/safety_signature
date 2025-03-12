import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_widget_from_html/flutter_widget_from_html.dart';
import 'package:go_router/go_router.dart';
import 'package:safety_signature_app/bulletin_board/model/approve_signature_model.dart';
import 'package:safety_signature_app/bulletin_board/model/bulletin_board_detail_model.dart';
import 'package:safety_signature_app/bulletin_board/model/bulletin_board_model.dart';
import 'package:safety_signature_app/bulletin_board/provider/approve_master_provider.dart';
import 'package:safety_signature_app/bulletin_board/provider/bulletin_board_provider.dart';
import 'package:safety_signature_app/common/components/card_container.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/layout/default_layout.dart';
import 'package:safety_signature_app/common/provider/attach_doc_master_provider.dart';

class BulletinBoardDetailScreen extends ConsumerStatefulWidget {
  static String get routeName => 'bulletinBoardDetailScreen';

  final String bulletinBoardId;

  const BulletinBoardDetailScreen({
    super.key,
    required this.bulletinBoardId,
  });
  @override
  ConsumerState<BulletinBoardDetailScreen> createState() =>
      _BulletinBoardDetailScreenState();
}

class _BulletinBoardDetailScreenState
    extends ConsumerState<BulletinBoardDetailScreen> {
  // 스크롤 동작시 동작해야할 함수를 정의하려면해당 과정 필요
  // final ScrollController controller = ScrollController();
  // void listener() {
  //
  // }
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    //화면 진입시 상세정보를 추가로 받아옴
    ref
        .read(bulletinBoardProvider.notifier)
        .getDetail(id: widget.bulletinBoardId);
    // controller.addListener(listener);
  }

  @override
  Widget build(BuildContext context) {
    final bulletinBoardDetail =
        ref.watch(bulletinBoardDetailProvider(widget.bulletinBoardId));

    if (bulletinBoardDetail == null) {
      return DefaultLayout(
          child: Center(
        child: CircularProgressIndicator(),
      ));
    }
    if (bulletinBoardDetail is BulletinBoardDetailModel) {
      return DefaultLayout(
        title: '전자결재 상세',
        child: _buildDetailContent(
            context: context,
            detail: bulletinBoardDetail,
            downloadFile: _downloadFile,
            handleSignature: _handleSignature),
        // body: bulletinDetail.when(
        //   data: (detail) => _buildDetailContent(context, detail),
        //   loading: () => Center(child: CircularProgressIndicator()),
        //   error: (err, stack) => Center(child: Text('데이터 로딩 실패')),
        // ),
      );
    }
    return DefaultLayout(
        child: Center(
      child: CircularProgressIndicator(),
    ));
  }

// ✅ 결재 처리
  void _handleSignature(String bulletinBoardId) async {
    // approveResult is ApproveSignatureMessageModel
    // 결재 처리 로직 추가
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(
          '결재 진행 중...',
          style: defaultTextStyle,
        ),
        duration: Duration(seconds: 2),
      ),
    );
    await ref
        .read(approveMasterProvider.notifier)
        .approveSignature(bulletinBoardId: bulletinBoardId)
        .then((_) {
      final state = ref.watch(approveMasterProvider);
      if (state is ApproveSignatureMessageModel) {
        state.httpStatus == 200
            ? ref
                .read(bulletinBoardProvider.notifier)
                .getDetail(id: widget.bulletinBoardId)
            : null;
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text(
              state.message,
              style: defaultTextStyle,
            ),
            duration: Duration(seconds: 2),
          ),
        );
      }
    }).catchError((_) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            '결재 실패 잠시 후 다시 시도해 주세요.',
            style: defaultTextStyle,
          ),
          duration: Duration(seconds: 2),
        ),
      );
    });
  }

  // ✅ 파일 다운로드 처리
  void _downloadFile({required String attachId, required String fileName}) {
    // 실제 파일 다운로드 처리 (예: url_launcher 사용)
    final attach = ref
        .watch(attachDocMasterProvider.notifier)
        .downloadFile(attachId: attachId, fileName: fileName);
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(
          '파일 다운로드 중 : $fileName',
          style: defaultTextStyle,
        ),
        duration: Duration(seconds: 2),
      ),
    );
  }
}

Widget _buildDetailContent(
    {required BuildContext context,
    required BulletinBoardDetailModel detail,
    required Function downloadFile,
    required Function handleSignature}) {
  return Padding(
    padding: EdgeInsets.all(16.0),
    child: Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          detail.boardTitle,
          style: defaultTextStyle.copyWith(
              fontSize: 25, fontWeight: FontWeight.bold),
        ),
        SizedBox(height: 8),
        Container(
          height: 1.0,
          width: MediaQuery.of(context).size.width,
          color: SECONDARY_COLOR,
        ),
        Text(
          "작성자: ${detail.userMasterDTO.name} • ${detail.createdDateFormat}",
          style: defaultTextStyle.copyWith(fontSize: 15, color: SUBTEXT_COLOR),
        ),
        // Text(detail?.siteAddress),
        SizedBox(height: 16),
        Expanded(
          child: CardContainer(
            child: Padding(
              padding: const EdgeInsets.all(24.0),
              child: SingleChildScrollView(
                child: HtmlWidget(
                  detail.boardContents,
                  textStyle: defaultTextStyle,
                ),
                // Text(
                //   detail.boardContents,
                //   style: defaultTextStyle,
                // ),
              ),
            ),
          ),
        ),
        const SizedBox(height: 16),
        if (detail.siteAddress != null) ...[
          Text(
            '현장 정보',
            style: defaultTextStyle.copyWith(
                fontSize: 20, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 8),
          Text(
            detail.siteAddress!,
            style: defaultTextStyle,
          ),
          const SizedBox(height: 16),
        ],

        // 🔹 첨부 파일 다운로드 버튼
        if (detail.attachYn) ...[
          Text(
            '첨부 파일',
            style: defaultTextStyle.copyWith(
                fontSize: 20, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 8),
          Column(
            children: detail.attachDocList.map((file) {
              return ListTile(
                leading: Icon(Icons.attach_file),
                title: Text(file.attachDocName, style: defaultTextStyle),
                trailing: IconButton(
                  icon: Icon(Icons.download),
                  onPressed: () {
                    downloadFile(
                        attachId: file.id, fileName: file.attachDocName);
                  },
                ),
              );
            }).toList(),
          ),
        ],
        // 🔹 결재하기 버튼 (completionYn == false 일 때만 활성화)
        SizedBox(
          width: double.infinity,
          child: ElevatedButton(
            onPressed: detail.completionYn
                ? null
                : () {
                    handleSignature(detail.id);
                  },
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.red,
              disabledBackgroundColor: SUBTEXT_COLOR,
              padding: EdgeInsets.all(16),
            ),
            child: Text('${detail.completionYn ? '완료' : "결재하기"}',
                style: defaultTextStyle.copyWith(fontSize: 18)),
          ),
        ),
      ],
    ),
  );
}
