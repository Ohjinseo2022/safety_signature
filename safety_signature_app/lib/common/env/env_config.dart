import 'package:envied/envied.dart';
part 'env_config.g.dart';

// 현재 실행 중인 `flavor`에 따라 환경 파일을 로드
// const String flavor = String.fromEnvironment('FLAVOR', defaultValue: 'local'); 버그가 있는듯..

@Envied(path: '.env')
abstract class Env {
  @EnviedField(varName: 'ENV_BASE_URL', obfuscate: true)
  static String ENV_BASE_URL = _Env.ENV_BASE_URL;
  @EnviedField(varName: 'ENV_ANDROID_IP', obfuscate: true)
  static String ENV_ANDROID_IP = _Env.ENV_ANDROID_IP;
  @EnviedField(varName: 'ENV_IOS_IP', obfuscate: true)
  static String ENV_IOS_IP = _Env.ENV_IOS_IP;
  @EnviedField(varName: 'KAKAO_NATIVE_APP_KEY', obfuscate: true)
  static String KAKAO_NATIVE_APP_KEY = _Env.KAKAO_NATIVE_APP_KEY;
  @EnviedField(varName: 'KAKAO_JAVASCRIPT_APP_KEY', obfuscate: true)
  static String KAKAO_JAVASCRIPT_APP_KEY = _Env.KAKAO_JAVASCRIPT_APP_KEY;
}
