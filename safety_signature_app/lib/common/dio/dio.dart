import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:safety_signature_app/common/const/data.dart';
import 'package:safety_signature_app/common/secure_storage/secure_storage.dart';

final dioProvider = Provider<Dio>((ref) {
  final dio = Dio();
  // 엑세스 토큰 및 리프레쉬 토큰 관리를 위함
  final storage = ref.watch(secureStorageProvider);
  dio.interceptors.add(CustomInterceptor(storage: storage, ref: ref));
  return dio;
});

class CustomInterceptor extends Interceptor {
  final FlutterSecureStorage storage;
  final Ref ref;
  CustomInterceptor({required this.storage, required this.ref});

  // 1) 요청 널을때
  @override
  void onRequest(
      RequestOptions options, RequestInterceptorHandler handler) async {
    // POST,GET,PUT,PATCH 등등 + 요청 URI 확인
    // print("[REQ] [${options.method}] ${options.uri}");
    // print("${options}");
    if (options.headers['accessToken'] == 'true') {
      //해더 엑세스 토큰 필요 유무 정보 삭제
      options.headers.remove('accessToken');
      //시큐어 스토리지에 저장해놓은 엑세스 토큰 정보
      final token = await storage.read(key: ACCESS_TOKEN_KEY);
      //헤더에 추가
      options.headers.addAll({'authorization': 'Bearer $token'});
    }
    if (options.headers['refreshToken'] == 'true') {
      //헤더에 리프레시 토큰 필요 유무 정보 삭제
      options.headers.remove('refreshToken');
      //시큐어 스토리지에 저장해놓은 리프레시 토큰 정보
      final token = await storage.read(key: REFRESH_TOKEN_KEY);
      //헤더에 추가
      options.headers.addAll({'authorization': 'Bearer $token'});
    }
    // TODO: implement onRequest
    super.onRequest(options, handler);
  }

  // 2) 응답 받을떄
  @override
  void onResponse(Response response, ResponseInterceptorHandler handler) {
    // TODO: implement onResponse
    // print("[RES] [${response.requestOptions}] ${response.requestOptions.uri}");
    super.onResponse(response, handler);
  }

  // 3) 에러 핸들링
  @override
  void onError(DioException err, ErrorInterceptorHandler handler) async {
    /**
     * 401에러가 났을떄 (status code)
     * 토큰을 재발급 받는 시도를하고 토근이 재발급되면
     * 다시 새로운 토큰으로 요청한다.
     * */
    // await storage.deleteAll();
    // print("[ERR] [${err.requestOptions.method}] ${err.requestOptions.uri}");
    final refreshToken = await storage.read(key: REFRESH_TOKEN_KEY);
    if (refreshToken == null) {
      //리프레시 토큰 차제가 없다면 그대로 에러를 뱉어냄
      return handler.reject(err);
    }
    final isStatus401 = err.response?.statusCode == 401;
    final isPathRefresh = err.requestOptions.path ==
        '$baseUrl/auth/token/renew'; //토큰을 새로 받급 받으려다가 에러난경우
    //토큰을 재발급 하려는게 아니고 다른 api를 호출했을때 401(권한에러? ) 가 났다면
    if (isStatus401 && !isPathRefresh) {
      final dio = Dio();
      try {
        final res = await dio.post('$ip$baseUrl/auth/token/renew',
            options: Options(headers: {
              'authorization': 'Bearer $refreshToken',
            }));
        //토큰 변경 하기
        final accessToken = res.data['accessToken'];
        final newRefreshToken = res.data['refreshToken'];
        final options = err.requestOptions;

        options.headers.addAll({'authorization': 'Bearer $accessToken'});
        await storage.write(key: ACCESS_TOKEN_KEY, value: accessToken);
        await storage.write(key: REFRESH_TOKEN_KEY, value: newRefreshToken);
        //요청 재전송
        final response = await dio.fetch(options);
        return handler.resolve(response);
      } on DioException catch (e) {
        //circular dependency error
        // A, B
        // A -> B 의 친구
        // B -> A 의 친구
        // A는 B 의 친구구나
        // A -> B -> A -> B -> A -> B.......
        // ump -> dio -> ump -> dio....
        //리프레시 토큰이 유효 하지 않다!
        // ref.read(authProvider.notifier).logout();
        return handler.reject(e);
      }
    }
    return handler.reject(err);
  }
}
