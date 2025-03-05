import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:open_file/open_file.dart';
import 'package:path_provider/path_provider.dart';
import 'package:safety_signature_app/common/repository/attach_doc_master_repository.dart';

final attachDocMasterProvider =
    StateNotifierProvider<AttachDocMasterStateNotifier, dynamic>((ref) {
  final attachDocMasterRepository =
      ref.watch(attachDocMasterRepositoryProvider);
  return AttachDocMasterStateNotifier(
      attachDocMasterRepository: attachDocMasterRepository);
});

class AttachDocMasterStateNotifier extends StateNotifier<dynamic> {
  final AttachDocMasterRepository attachDocMasterRepository;

  AttachDocMasterStateNotifier({required this.attachDocMasterRepository})
      : super(dynamic);

  Future<String?> downloadFile(
      {required String attachId, required String fileName}) async {
    try {
      // ✅ 2. 저장할 파일 경로 가져오기
      final Directory directory = await getApplicationDocumentsDirectory();
      final String filePath = "${directory.path}/$fileName";

      // print("📂 다운로드 위치: $filePath");

      // ✅ 3. 서버에서 파일 다운로드 요청
      final response = await attachDocMasterRepository.downloadFile(
          attachId, Options(responseType: ResponseType.bytes));

      // ✅ 4. 파일 저장
      File file = File(filePath);
      await file.writeAsBytes(response); // 파일 저장
      // print("✅ 파일 다운로드 완료: $filePath");
      OpenFile.open(filePath);
      return filePath;
    } catch (e) {
      // print("❌ 파일 다운로드 오류: $e");
      return null;
    }
  }
}
