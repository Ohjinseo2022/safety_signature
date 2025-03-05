import 'package:flutter/cupertino.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:safety_signature_app/bulletin_board/view/bulletin_board_detail_screen.dart';
import 'package:safety_signature_app/bulletin_board/view/bulletin_board_list_screen.dart';
import 'package:safety_signature_app/common/view/root_tab.dart';
import 'package:safety_signature_app/user/provider/user_auth_provider.dart';
import 'package:safety_signature_app/user/view/email_login_screen.dart';
import 'package:safety_signature_app/user/view/join_screen.dart';

final authProvider = ChangeNotifierProvider<AuthProvider>((ref) {
  return AuthProvider(ref: ref);
});

class AuthProvider extends ChangeNotifier {
  final Ref ref;

  AuthProvider({required this.ref}) {
    ref.listen(userAuthProvider, (previous, next) {
      if (previous != next) {
        // 라우팅 이 변경될때만 동작하게 설정
        notifyListeners();
      }
    });
  }

  List<GoRoute> get routes => [
        GoRoute(
            path: '/',
            name: RootTab.routeName,
            builder: (_, state) => RootTab(),
            routes: [
              GoRoute(
                path: 'join',
                name: JoinScreen.routeName,
                builder: (_, state) => JoinScreen(),
              ),
              GoRoute(
                path: "email-login",
                name: EmailLoginScreen.routeName,
                builder: (_, state) => EmailLoginScreen(),
              ),
              GoRoute(
                  path: "bulletin-board",
                  name: BulletinBoardListScreen.routeName,
                  builder: (_, state) => BulletinBoardListScreen(),
                  routes: [
                    GoRoute(
                      path: 'detail/:rid',
                      name: BulletinBoardDetailScreen.routeName,
                      builder: (_, state) => BulletinBoardDetailScreen(
                          bulletinBoardId: state.pathParameters['rid']!),
                    )
                  ])
              // GoRoute(
              //   path: "chat-room/:id",
              //   name: ChatRoomDetail.routeName,
              //   builder: (_, state) => ChatRoomDetail(
              //     chatRoomId: state.pathParameters['id']!,
              //   ),
              // ),
            ])
      ];

  /**
   * 첫 시작은 스플래시 스크린
   * 앱 시작 후 로그인 됐는지 확인 필요 . 로그인이 필수는 아니지만
   * 로그인 기능이 필요한 경우 로그인 처리를 미리 해놓을 필요가 있음 !
   */
  String? redirectLogic(GoRouterState state) {
    return null;
    // // print(state.location);
    // final loginGo = state.location == '/login';
    // // if (state.location == '/splash') {
    // //
    // //   return '/login';
    // // }
    // if (loginGo) {
    //   await Future.delayed(Duration(milliseconds: 1500));
    //   return '/login';
    // } else {
    //   return '/splash';
    // }
  }
}
