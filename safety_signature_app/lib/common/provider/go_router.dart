import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:safety_signature_app/user/provider/auth_provider.dart';

final routerProvider = Provider<GoRouter>((ref) {
  final provider = ref.read(authProvider);
  return GoRouter(
    routes: provider.routes,
    // initialLocation: '/splash',
    redirect: (_, state) => provider.redirectLogic(state),
    debugLogDiagnostics: true,
  );
});
