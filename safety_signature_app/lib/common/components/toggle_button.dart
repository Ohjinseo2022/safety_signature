import 'package:flutter/material.dart';

class ToggleButton extends StatelessWidget {
  final bool isToggle;
  const ToggleButton({super.key, required this.isToggle});
  @override
  Widget build(BuildContext context) {
    return AnimatedSwitcher(
      duration: Duration(milliseconds: 500),
      child: Icon(isToggle ? Icons.toggle_on : Icons.toggle_off_outlined),
    );
  }
}
