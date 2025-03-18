import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:safety_signature_app/bulletin_board/view/bulletin_board_list_screen.dart';
import 'package:safety_signature_app/common/components/common_dialog.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/enumeration/user_status_code.dart';
import 'package:safety_signature_app/common/layout/default_layout.dart';
import 'package:safety_signature_app/common/model/permission_model.dart';
import 'package:safety_signature_app/common/provider/permission_provider.dart';
import 'package:safety_signature_app/common/view/splash_screen.dart';
import 'package:safety_signature_app/user/model/user_model.dart';
import 'package:safety_signature_app/user/provider/user_auth_provider.dart';
import 'package:safety_signature_app/user/view/join_screen.dart';
import 'package:safety_signature_app/user/view/login_screen.dart';
import 'package:safety_signature_app/user/view/my_page_screen.dart';

class RootTab extends ConsumerStatefulWidget {
  static String get routeName => 'rootTab';
  final int indexNumber;
  const RootTab({super.key, this.indexNumber = 0});

  @override
  ConsumerState<RootTab> createState() => _RootTabState();
}

class _RootTabState extends ConsumerState<RootTab>
    with TickerProviderStateMixin {
  late TabController controller;
  late AnimationController animationController;
  late int index = widget.indexNumber;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    animationController = BottomSheet.createAnimationController(this);
    animationController
      ..duration = Duration(milliseconds: 500)
      ..reverseDuration = Duration(milliseconds: 500);
    controller = TabController(length: 2, vsync: this);
    controller.addListener(tabListener);
  }

  //bottomNavigationBar 와 TabBarView 를 연동시키는 방법 !
  void tabListener() {
    setState(() {
      index = controller.index;
    });
  }

  @override
  void dispose() {
    animationController.dispose();
    controller.removeListener(tabListener);
    // TODO: implemet dispose

    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final state = ref.watch(userAuthProvider);
    final permission = ref.watch(permissionProvider);
    if (state is UserMinModel &&
        UserStatusCode.getByCode(state.userStatusCode) ==
            UserStatusCode.PENDING) {
      ModalRoute.of(context)!.isCurrent
          ? WidgetsBinding.instance.addPostFrameCallback((_) {
              commonDialog(
                context: context,
                title: "회원가입 안내",
                content: Center(
                    child: Text(
                  "전자 서명 정보 등록 후 간편가입 완료됩니다.",
                  style: defaultTextStyle,
                )),
                onConfirm: () async {
                  final result = await context.pushNamed(JoinScreen.routeName);
                  if (result == null) {
                    await ref.watch(userAuthProvider.notifier).userLogout();
                  }
                },
              );
            })
          : null;

      return AnimatedSwitcher(
          duration: Duration(milliseconds: 500),
          child: DefaultLayout(
            child: LoginScreen(),
          ));
    }

    return AnimatedSwitcher(
        duration: Duration(milliseconds: 500),
        child:
            permission is PermissionLoading || permission is! PermissionGranted
                ? SplashScreen()
                : _rootTab(state: state));
  }

  Widget _rootTab({required UserModelBase? state}) {
    List<dynamic> tabList = [
      {"title": "전자결재 게시판", "child": BulletinBoardListScreen()},
      // {"title": "테스트중", "child": MyPageScreen()},
      {
        "title": state is UserMinModel ? "메이페이지" : null,
        "child": MyPageScreen()
      },
    ];
    if (state is UserModelLoading
        //    || state is UserModelError
        ) {
      return SplashScreen();
    }
    if (state is UserModelError) {
      state = UserModelGuest();
    }
    if (state is UserModelGuest) {
      // Future.delayed(Duration(milliseconds: 1000));
      return DefaultLayout(
        child: LoginScreen(),
      );
    }
    return DefaultLayout(
      title: tabList[index]["title"],
      child: TabBarView(
        physics: NeverScrollableScrollPhysics(),
        controller: controller,
        children: tabList.map((e) => e["child"] as Widget).toList(),
      ),
      bottomNavigationBar: BottomNavigationBar(
        selectedItemColor: TEXT_COLOR,
        unselectedItemColor: SECONDARY_COLOR,
        backgroundColor: BACK_GROUND_COLOR,
        elevation: 1,
        selectedFontSize: 10,
        unselectedFontSize: 10,
        type: BottomNavigationBarType.fixed,
        onTap: (int index) {
          // print(state);
          // if (index == 1 && state is UserModelGuest) {
          //   loginBottomSheet(context, animationController);
          //   return;
          // }
          controller.animateTo(index);
        },
        currentIndex: index,
        items: [
          BottomNavigationBarItem(
            icon: Icon(index == 0 ? Icons.check_box : Icons.check_box_outlined),
            label: "전자결재",
          ),
          BottomNavigationBarItem(
              icon: Icon(
                index == 1
                    ? Icons.account_circle
                    : Icons.account_circle_outlined,
              ),
              label: state is UserMinModel ? "마이페이지" : "로그인"),
        ],
      ),
      //TODO : 화면 구성이 다양해 지면 추가할 예정임
    );
  }
}
