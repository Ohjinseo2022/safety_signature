import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class LoginButton extends StatelessWidget {
  final VoidCallback? onPressed;
  final String svgPath;
  final String label;
  final Color? labelColor;
  final Color backgroundColor;
  final double? iconHeight;
  const LoginButton(
      {super.key,
      required this.onPressed,
      required this.svgPath,
      required this.label,
      required this.backgroundColor,
      this.labelColor = Colors.white,
      this.iconHeight = 40.0});

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: MediaQuery.of(context).size.width / 1.5,
      child: ElevatedButton.icon(
        onPressed: onPressed,
        icon: SvgPicture.asset(
          svgPath,
          height: iconHeight,
        ),
        style: ElevatedButton.styleFrom(
            foregroundColor: Colors.white,
            backgroundColor: backgroundColor,
            padding: EdgeInsets.symmetric(horizontal: 8),
            shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10))),
        label: SizedBox(
          width: MediaQuery.of(context).size.width / 2,
          child: Padding(
            padding: const EdgeInsets.only(right: 16),
            child: Text(
              label,
              textAlign: TextAlign.center,
              style: TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 18,
                  height: 3,
                  color: labelColor),
            ),
          ),
        ),
      ),
    );
  }
}
