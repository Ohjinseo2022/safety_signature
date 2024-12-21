import 'package:flutter/material.dart';
import 'package:safety_signature_app/common/const/color.dart';

class DefaultLayout extends StatelessWidget {
  final Color? backgroundColor;
  final Widget child;
  final String? title;
  final Widget? bottomNavigationBar;
  final Widget? floatingActionButton;
  final FloatingActionButtonLocation? floatingActionButtonLocation;
  final bool? resizeToAvoidBottomInset;
  final bool? topAppBarBtn;
  final VoidCallback? searchOnPressed;
  final VoidCallback? personOnPressed;
  final VoidCallback? menuBarOnPressed;
  const DefaultLayout({
    super.key,
    required this.child,
    this.resizeToAvoidBottomInset,
    this.backgroundColor = BACK_GROUND_COLOR,
    this.title,
    this.topAppBarBtn = true,
    this.bottomNavigationBar,
    this.floatingActionButton,
    this.floatingActionButtonLocation,
    this.menuBarOnPressed,
    this.personOnPressed,
    this.searchOnPressed,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: resizeToAvoidBottomInset,
      backgroundColor: backgroundColor,
      appBar: renderAppBar(context: context),
      body: child,
      bottomNavigationBar: bottomNavigationBar,
      floatingActionButton: floatingActionButton,
      floatingActionButtonLocation: floatingActionButtonLocation,
    );
  }

  AppBar? renderAppBar({required BuildContext context}) {
    if (title == null) {
      return null;
    } else {
      return AppBar(
        backgroundColor: BACK_GROUND_COLOR,
        //튀어나옴 효과
        elevation: 0,
        actions: topAppBarBtn == true
            ? [
                IconButton(
                    onPressed: () {},
                    icon: Icon(
                      Icons.search,
                      color: SELECT_TEXT_COLOR,
                    )),
                IconButton(
                    onPressed: () {},
                    icon: Icon(
                      Icons.person,
                      color: SELECT_TEXT_COLOR,
                    )),
                IconButton(
                    onPressed: () {},
                    icon: Icon(
                      Icons.menu,
                      color: SELECT_TEXT_COLOR,
                    )),
              ]
            : [],
        title: Padding(
          padding: EdgeInsets.symmetric(
              horizontal: MediaQuery.of(context).size.width / 60),
          child: Text(
            title!,
            style: defaultTextStyle.copyWith(
              fontSize: 24,
              fontWeight: FontWeight.w800,
            ),
          ),
        ),
        foregroundColor: Colors.black,
      );
    }
  }
}
