import 'dart:convert';

import 'package:safety_signature_app/common/const/data.dart';

class DataUtils {
  static DateTime stringToDateTime(String value) {
    return DateTime.parse(value).toUtc().add(Duration(hours: 9));
  }

  static String pathToUrl(String value) {
    return "http://$ip$value";
  }

  static List<String> listPathsToUrls(List paths) {
    return paths.map((e) => pathToUrl(e)).toList();
  }

  static String plainToBase64(String plain) {
    Codec<String, String> stringToBase64 = utf8.fuse(base64);
    String encoded = stringToBase64.encode(plain);

    return encoded;
  }

  static String? phoneRegex(String? value) {
    if (value == null && value == "") {
      return null;
    }
    final RegExp phoneRegex = RegExp(r'^010\d{7,8}$');
    if (phoneRegex.hasMatch(value!)) {
      return null;
    } else {
      return '유효하지 않은 핸드폰번호입니다.';
    }
  }

  static String? passwordRegex(String? value) {
    if (value == null && value == "") {
      return null;
    }
    final RegExp passwordRegex = RegExp(
        r'^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>])[A-Za-z\d!@#$%^&*(),.?":{}|<>]{8,}$');
    if (passwordRegex.hasMatch(value!)) {
      return null;
    } else {
      return '대/소문자,숫자,특수문자를 포함한 최소 8자 이상 입력 해주세요.';
    }
  }

  static String? emailRegex(String? value) {
    if (value == null && value == "") {
      return null;
    }
    final RegExp emailRegex =
        RegExp(r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$');
    if (emailRegex.hasMatch(value!)) {
      return null;
    } else {
      return '이메일 형식이 맞지 않습니다.';
    }
  }
}
