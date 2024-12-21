import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/layout/default_layout.dart';
import 'package:safety_signature_app/common/model/permission_model.dart';
import 'package:safety_signature_app/common/provider/permission_provider.dart';
import 'package:safety_signature_app/common/view/splash_screen.dart';
import 'package:safety_signature_app/user/model/user_model.dart';
import 'package:safety_signature_app/user/provider/user_auth_provider.dart';
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
    controller = TabController(length: 3, vsync: this);
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
    return AnimatedSwitcher(
        duration: Duration(milliseconds: 500),
        child:
            permission is PermissionLoading || permission is! PermissionGranted
                ? SplashScreen()
                : _rootTab(state: state));
  }

  Widget _rootTab({required UserModelBase? state}) {
    List<dynamic> tabList = [
      // {"title": "AMATTA", "child": MainListScreen()},
      {"title": "테스트중", "child": MyPageScreen()},

      // {"title": "카테고리", "child": CategoryScreen()},
      {"title": "테스트중", "child": MyPageScreen()},
      // {"title": "메세지", "child": ChatRoomList()},
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
    return DefaultLayout(
      title: tabList[index]["title"],
      child: TabBarView(
        physics: NeverScrollableScrollPhysics(),
        controller: controller,
        children: tabList.map((e) => e["child"] as Widget).toList(),
      ),
      bottomNavigationBar: BottomNavigationBar(
        selectedItemColor: SELECT_TEXT_COLOR,
        unselectedItemColor: UNSELECT_TEXT_COLOR,
        backgroundColor: BACK_GROUND_COLOR,
        elevation: 1,
        selectedFontSize: 10,
        unselectedFontSize: 10,
        type: BottomNavigationBarType.fixed,
        onTap: (int index) {
          if (index == 2 && state is UserModelGuest) {
            loginBottomSheet(context, animationController);
            return;
          }
          controller.animateTo(index);
        },
        currentIndex: index,
        items: [
          BottomNavigationBarItem(
            icon: Icon(index == 0
                ? Icons.calendar_month
                : Icons.calendar_month_outlined),
            label: "아마따",
          ),
          BottomNavigationBarItem(
              icon: Icon(index == 1
                  ? Icons.content_paste
                  : Icons.content_paste_outlined),
              label: "아차차"),
          // BottomNavigationBarItem(
          //     icon:
          //         Icon(index == 2 ? Icons.groups_2 : Icons.groups_2_outlined),
          //     label: "메세지"),
          // BottomNavigationBarItem(
          //     icon: Icon(Icons.accessibility_new_outlined), label: "로그인"),
          BottomNavigationBarItem(
              icon: Icon(
                index == 3
                    ? Icons.account_circle
                    : Icons.account_circle_outlined,
              ),
              label: state is UserMinModel ? "마이페이지" : "로그인"),
        ],
      ),
      //TODO : 화면 구성이 다양해 지면 추가할 예정임
      // floatingActionButton:
      //     // _floatingActionButtons(),
      //     CustomFloatingActionButton(
      //   distance: 70,
      //   children: [
      //     ActionButton(
      //       onPressed: () {},
      //       icon: Icon(
      //         Icons.add_chart,
      //       ),
      //       label: "대시보드",
      //     ),
      //     ActionButton(
      //       onPressed: () {},
      //       icon: Icon(Icons.handshake),
      //       label: "일정추가",
      //     ),
      //     ActionButton(
      //       onPressed: () {},
      //       icon: Icon(Icons.group_add),
      //       label: "방만들기",
      //     ),
      //     ActionButton(
      //       onPressed: () {},
      //       icon: Icon(
      //         Icons.settings,
      //       ),
      //       label: "설정",
      //     ),
      //   ],
      // ),
      // floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
    );
  }
}
