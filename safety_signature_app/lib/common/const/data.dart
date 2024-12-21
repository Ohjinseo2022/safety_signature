import 'dart:io';
import 'package:safety_signature_app/common/env/env_config.dart';

const ACCESS_TOKEN_KEY = 'ACCESS_TOKEN';
const REFRESH_TOKEN_KEY = 'REFRESH_TOKEN';

//현재 실행 환경 파악
// 애플에서 시뮬레이터를 쓸떄는 - 시뮬레이터와 네트워크 환경이 똑같음
// 안드로이드의 경우 애뮬레이터와 컴퓨터와 네트워크가 다름
var emulatorIp = Env.ENV_ANDROID_IP; //'10.0.2.2:8080'; //안드 localhost
var simulatorIp = Env.ENV_IOS_IP; // '127.0.0.1:8080';
var baseUrl = Env.ENV_BASE_URL; //"/services/backend";
final String ip = Platform.isIOS ? simulatorIp : emulatorIp;
