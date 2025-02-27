import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

class BulletinBoardListScreen extends ConsumerStatefulWidget {
  static String get routeName => 'bulletinBoardScreen';
  const BulletinBoardListScreen({super.key});

  @override
  ConsumerState<BulletinBoardListScreen> createState() =>
      _BulletinBoardListScreenState();
}

class _BulletinBoardListScreenState
    extends ConsumerState<BulletinBoardListScreen> {
  @override
  Widget build(BuildContext context) {
    return const Placeholder();
  }
}
