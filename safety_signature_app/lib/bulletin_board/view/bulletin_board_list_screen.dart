import 'package:flutter/material.dart';
import 'package:safety_signature_app/bulletin_board/components/bulletin_board_card.dart';
import 'package:safety_signature_app/bulletin_board/provider/bulletin_board_provider.dart';
import 'package:safety_signature_app/bulletin_board/view/bulletin_board_detail_screen.dart';
import 'package:safety_signature_app/common/components/pagination_list_view.dart';
import 'package:go_router/go_router.dart';

class BulletinBoardListScreen extends StatelessWidget {
  static String get routeName => 'bulletinBoardScreen';
  const BulletinBoardListScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return PaginationListView(
        provider: bulletinBoardProvider,
        itemBuilder: <BulletinBoardModel>(_, index, model) {
          return GestureDetector(
            onTap: () {
              context.pushNamed(BulletinBoardDetailScreen.routeName,
                  pathParameters: {'rid': model.id});
            },
            child: BulletinBoardCard.fromModel(model: model),
          );
        });
  }
}
