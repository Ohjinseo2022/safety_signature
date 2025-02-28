import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_widget_from_html/flutter_widget_from_html.dart';
import 'package:go_router/go_router.dart';
import 'package:safety_signature_app/bulletin_board/model/bulletin_board_detail_model.dart';
import 'package:safety_signature_app/bulletin_board/model/bulletin_board_model.dart';
import 'package:safety_signature_app/bulletin_board/provider/bulletin_board_provider.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/layout/default_layout.dart';

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
    print(bulletinBoardDetail.id);
    return DefaultLayout(
      title: '전자결제 상세',
      child: _buildDetailContent(context, bulletinBoardDetail),
      // body: bulletinDetail.when(
      //   data: (detail) => _buildDetailContent(context, detail),
      //   loading: () => Center(child: CircularProgressIndicator()),
      //   error: (err, stack) => Center(child: Text('데이터 로딩 실패')),
      // ),
    );
  }
}

Widget _buildDetailContent(BuildContext context, BulletinBoardModel detail) {
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
          "작성자: ${detail.createdBy} • ${detail.createdDateFormat}",
          style: defaultTextStyle.copyWith(fontSize: 15, color: SUBTEXT_COLOR),
        ),

        SizedBox(height: 8),
        SizedBox(height: 16),
        Expanded(
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
        SizedBox(height: 16),

        // 🔹 첨부 파일 다운로드 버튼
        if (detail is BulletinBoardDetailModel && detail.attachYn) ...[
          Text(
            '첨부 파일',
            style: defaultTextStyle.copyWith(
                fontSize: 20, fontWeight: FontWeight.bold),
          ),
          SizedBox(height: 8),
          Column(
            children: detail.attachDocList.map((file) {
              return ListTile(
                leading: Icon(Icons.attach_file),
                title: Text(file.attachDocName, style: defaultTextStyle),
                trailing: IconButton(
                  icon: Icon(Icons.download),
                  onPressed: () {
                    _downloadFile(context, file.id);
                  },
                ),
              );
            }).toList(),
          ),
        ],

        // 🔹 결제하기 버튼 (completionYn == false 일 때만 표시)
        if (!detail.completionYn)
          SizedBox(
            width: double.infinity,
            child: ElevatedButton(
              onPressed: () {
                _handlePayment(context, detail.id);
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.red,
                padding: EdgeInsets.all(16),
              ),
              child:
                  Text('결제하기', style: defaultTextStyle.copyWith(fontSize: 18)),
            ),
          ),
      ],
    ),
  );
}

// ✅ 파일 다운로드 처리
void _downloadFile(BuildContext context, String fileUrl) {
  // 실제 파일 다운로드 처리 (예: url_launcher 사용)
  ScaffoldMessenger.of(context).showSnackBar(
    SnackBar(
        content: Text(
      '파일 다운로드: $fileUrl',
      style: defaultTextStyle,
    )),
  );
}

// ✅ 결제 처리
void _handlePayment(BuildContext context, String bulletinBoardId) {
  // 결제 처리 로직 추가
  ScaffoldMessenger.of(context).showSnackBar(
    SnackBar(
        content: Text(
      '결제 진행 중...',
      style: defaultTextStyle,
    )),
  );
}
