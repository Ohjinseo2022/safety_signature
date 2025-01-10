import 'package:flutter_riverpod/flutter_riverpod.dart';

final modalControllerProvider =
    StateNotifierProvider<ModalControllerProvider, bool>((ref) {
  return ModalControllerProvider();
});

class ModalControllerProvider extends StateNotifier<bool> {
  ModalControllerProvider() : super(true) {
    initController();
  }
  Future<void> isPopUp({required bool Visibility}) async {
    state = Visibility;
  }

  Future<void> initController() async {
    state = true;
  }
}
