import 'dart:math';
import 'package:flutter/material.dart';
import 'package:safety_signature_app/common/const/color.dart';

const Duration _duration = Duration(milliseconds: 300);

class CustomFloatingActionButton extends StatefulWidget {
  final double distance;
  final List<Widget> children;
  const CustomFloatingActionButton(
      {super.key, required this.distance, required this.children});

  @override
  State<CustomFloatingActionButton> createState() =>
      _CustomFloatingActionButtonState();
}

class _CustomFloatingActionButtonState extends State<CustomFloatingActionButton>
    with SingleTickerProviderStateMixin {
  // 애니 메이션 효과 도와주는 친구
  bool _open = false;
  late AnimationController _animationController;
  late Animation<double> _expandAnimation;
  @override
  void initState() {
    _animationController = AnimationController(
        value: _open ? 1.0 : 0.0, duration: _duration, vsync: this);
    //curve -> 움직이는 속도 또는 방식지정
    _expandAnimation = CurvedAnimation(
      parent: _animationController,
      curve: Curves.fastOutSlowIn,
      // reverseCurve: Curves.fastOutSlowIn,
    );
    super.initState();
  }

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox.expand(
      child: Stack(
        alignment: Alignment.bottomCenter,
        children: [
          if (_open) _backgroundContainer(),
          ..._buildExpandableActionButton(),
          _buildTabToCloseFab(),
          _buildTabToOpenFab(),
        ],
      ),
    );
  }

  List<_ExpandableActionButton> _buildExpandableActionButton() {
    List<_ExpandableActionButton> animaChildren = [];

    final int count = widget.children.length;
    final double gap = 150.0 / (count - 1);
    for (var i = 0, degree = 15.0; i < count; i++, degree += gap) {
      animaChildren.add(_ExpandableActionButton(
          distance: widget.distance,
          degree: degree,
          progress: _expandAnimation,
          child: AnimatedOpacity(
              duration: _duration,
              opacity: _open ? 1.0 : 0.0,
              child: widget.children[i])));
    }
    return animaChildren;
  }

  AnimatedContainer _backgroundContainer() {
    return AnimatedContainer(
      duration: _duration,
      transformAlignment: Alignment.center,
      child: GestureDetector(
        onTap: toggle,
        child: Container(
          // color: UNTEXT_PRIMARY_COLOR.withOpacity(0.5),
          decoration: BoxDecoration(
            gradient: LinearGradient(
              begin: Alignment.topCenter,
              end: Alignment.bottomCenter,
              colors: [
                TEXT_COLOR.withOpacity(1.0),
                TEXT_COLOR.withOpacity(0.0),
              ],
            ),
          ),
          width: double.infinity,
          height: double.infinity,
        ),
      ),
    );
  }

  AnimatedContainer _buildTabToCloseFab() {
    return AnimatedContainer(
      duration: _duration,
      padding: EdgeInsets.all(30.0),
      transformAlignment: Alignment.center,
      transform: Matrix4.rotationZ(_open ? 0 : pi / 4),
      child: FloatingActionButton(
        heroTag: "tabToClose",
        backgroundColor: BACK_GROUND_COLOR,
        onPressed: toggle,
        child: Icon(Icons.close, color: SECONDARY_COLOR),
        shape: CircleBorder(),
      ),
    );
  }

  AnimatedContainer _buildTabToOpenFab() {
    return AnimatedContainer(
      duration: _duration,
      padding: EdgeInsets.all(30.0),
      transformAlignment: Alignment.center,
      transform: Matrix4.rotationZ(_open ? 0 : pi / 4),
      child: AnimatedOpacity(
        duration: _duration,
        opacity: _open ? 0.0 : 1.0,
        child: FloatingActionButton(
          heroTag: "tabToOpen",
          // backgroundColor: UNTEXT_PRIMARY_COLOR,
          backgroundColor: BACK_GROUND_COLOR,
          onPressed: toggle,
          child: Icon(
            Icons.close,
            color: TEXT_COLOR,
          ),
          shape: CircleBorder(),
        ),
      ),
    );
  }

  void toggle() {
    _open = !_open;
    setState(() {
      if (_open) {
        _animationController.forward();
      } else {
        _animationController.reverse();
      }
    });
  }
}

class _ExpandableActionButton extends StatelessWidget {
  final double distance; // 거리
  final double degree; // 각도
  final Animation<double> progress;
  final Widget child;
  const _ExpandableActionButton({
    super.key,
    required this.distance,
    required this.degree,
    required this.progress,
    required this.child,
  });

  @override
  Widget build(BuildContext context) {
    return AnimatedBuilder(
      animation: progress,
      builder: (context, child) {
        // final Offset floatingPostion =
        final Offset offset = Offset.fromDirection(
          degree * (pi / 180), // 움직이는 각도
          progress.value * distance, //이동 거리 퍼센트
        );
        return Positioned(
          right: MediaQuery.of(context).size.width / 2 + offset.dx - 36,
          bottom: offset.dy + 30,
          child: child!,
        );
      },
      child: child,
    );
  }
}
