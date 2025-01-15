import 'package:flutter_riverpod/flutter_riverpod.dart';

final modalControllerProvider =
    StateNotifierProvider<ModalControllerProvider, bool>((ref) {
  return ModalControllerProvider();
});

class ModalControllerProvider extends StateNotifier<bool> {
  ModalControllerProvider() : super(true) {
    initController();
  }
  Future<void> isPopUp({required bool visibility}) async {
    state = visibility;
  }

  Future<void> initController() async {
    state = true;
  }
}
