import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:safety_signature_app/bulletin_board/model/bulletin_board_model.dart';
import 'package:safety_signature_app/bulletin_board/provider/bulletin_board_provider.dart';
import 'package:safety_signature_app/common/const/color.dart';

class BulletinBoardDetailScreen extends ConsumerWidget {
  static String get routeName => 'bulletinBoardDetailScreen';

  final String bulletinBoardId;

  const BulletinBoardDetailScreen({
    super.key,
    required this.bulletinBoardId,
  });

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final bulletinDetail = ref.watch(bulletinBoardProvider);

    return Scaffold(
      appBar: AppBar(
        title: Text(
          '게시글 상세',
          style: defaultTextStyle.copyWith(
              fontWeight: FontWeight.bold, fontSize: 24),
        ),
        backgroundColor: BACK_GROUND_COLOR,
      ),
      // body: bulletinDetail.when(
      //   data: (detail) => _buildDetailContent(context, detail),
      //   loading: () => Center(child: CircularProgressIndicator()),
      //   error: (err, stack) => Center(child: Text('데이터 로딩 실패')),
      // ),
    );
  }

  // Widget _buildDetailContent(
  //     BuildContext context, BulletinBoardModel detail) {
  //   return Padding(
  //     padding: EdgeInsets.all(16.0),
  //     child: Column(
  //       crossAxisAlignment: CrossAxisAlignment.start,
  //       children: [
  //         Text(
  //           detail.boardTitle,
  //           style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
  //         ),
  //         SizedBox(height: 8),
  //         Text("작성자: ${detail.createdBy} • ${detail.createdDateFormat}"),
  //         SizedBox(height: 16),
  //         Expanded(
  //           child: SingleChildScrollView(
  //             child: Text(detail.boardContents),
  //           ),
  //         ),
  //         SizedBox(height: 16),
  //
  //         // 🔹 첨부 파일 다운로드 버튼
  //         if (detail.attachments.isNotEmpty) ...[
  //           Text('첨부 파일',
  //               style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
  //           SizedBox(height: 8),
  //           Column(
  //             children: detail.attachments.map((file) {
  //               return ListTile(
  //                 leading: Icon(Icons.attach_file),
  //                 title: Text(file.fileName),
  //                 trailing: IconButton(
  //                   icon: Icon(Icons.download),
  //                   onPressed: () {
  //                     _downloadFile(context, file.fileUrl);
  //                   },
  //                 ),
  //               );
  //             }).toList(),
  //           ),
  //         ],
  //
  //         // 🔹 결제하기 버튼 (completionYn == false 일 때만 표시)
  //         if (!detail.completionYn)
  //           SizedBox(
  //             width: double.infinity,
  //             child: ElevatedButton(
  //               onPressed: () {
  //                 _handlePayment(context, detail.id);
  //               },
  //               style: ElevatedButton.styleFrom(
  //                 backgroundColor: Colors.red,
  //                 padding: EdgeInsets.all(16),
  //               ),
  //               child: Text('결제하기', style: TextStyle(fontSize: 18)),
  //             ),
  //           ),
  //       ],
  //     ),
  //   );
  // }

  // ✅ 파일 다운로드 처리
  void _downloadFile(BuildContext context, String fileUrl) {
    // 실제 파일 다운로드 처리 (예: url_launcher 사용)
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('파일 다운로드: $fileUrl')),
    );
  }

  // ✅ 결제 처리
  void _handlePayment(BuildContext context, String bulletinBoardId) {
    // 결제 처리 로직 추가
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('결제 진행 중...')),
    );
  }
}
