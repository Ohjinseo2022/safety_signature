import 'package:flutter/material.dart';
import 'package:safety_signature_app/bulletin_board/model/bulletin_board_model.dart';
import 'package:safety_signature_app/common/components/card_container.dart';
import 'package:safety_signature_app/common/const/color.dart';

class BulletinBoardCard extends StatelessWidget {
  final String id;
  final String title;
  final String createdBy;
  final String createdDateFormat;
  final bool? completionYn;

  const BulletinBoardCard({
    super.key,
    required this.id,
    required this.title,
    required this.createdBy,
    required this.createdDateFormat,
    required this.completionYn,
  });
  factory BulletinBoardCard.fromModel({required BulletinBoardModel model}) {
    return BulletinBoardCard(
      title: model.boardTitle,
      id: model.id,
      createdBy: model.userMasterDTO.name ?? '',
      createdDateFormat: model.createdDateFormat,
      completionYn: model.completionYn,
    );
  }
  @override
  Widget build(BuildContext context) {
    return CardContainer(
      child: Padding(
        padding: EdgeInsets.all(16.0),
        child: Row(
          mainAxisAlignment:
              MainAxisAlignment.spaceBetween, // 왼쪽 텍스트 + 오른쪽 아이콘 배치
          children: [
            // 🔹 왼쪽: 제목 + 작성자 정보
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    title,
                    style: defaultTextStyle.copyWith(
                        fontSize: 18, fontWeight: FontWeight.bold),
                    maxLines: 1, // 한 줄까지만 표시
                    overflow: TextOverflow.ellipsis, // 길어지면 "..." 처리
                  ),
                  SizedBox(height: 8),
                  Text(
                    "작성자 : $createdBy • $createdDateFormat",
                    style: defaultTextStyle.copyWith(
                        fontSize: 16,
                        fontWeight: FontWeight.w300,
                        color: SUBTEXT_COLOR),
                  ),
                ],
              ),
            ),

            // 🔹 오른쪽: 결재 완료 여부 아이콘
            if (completionYn ?? false)
              Icon(Icons.check_circle, color: Colors.green, size: 24)
            else
              Icon(Icons.cancel, color: Colors.red, size: 24),
          ],
        ),
      ),
    );
  }
}
