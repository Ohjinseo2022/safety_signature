#!/bin/bash

FLAVOR=$1  # 실행할 flavor 값
BUILD_TYPE=$2  # apk, aab, ios 선택

if [ -z "$FLAVOR" ]; then
  echo "❌ 실행할 flavor를 입력하세요! (예: local, staging, prod)"
  exit 1
fi

if [ -z "$BUILD_TYPE" ]; then
  echo "❌ 빌드 타입을 입력하세요! (apk, aab, ios)"
  exit 1
fi

echo "🛠 실행 환경: $FLAVOR"
echo "📦 빌드 타입: $BUILD_TYPE"

# 현재 환경 파일과 새로운 환경 파일이 다르면 변경
if ! cmp -s .env .env.$FLAVOR; then
  echo "🔄 환경 변수 변경 감지, .env 업데이트 중..."
  cp .env.$FLAVOR .env
  flutter clean
  flutter pub get
  flutter pub run build_runner build --delete-conflicting-outputs
else
  echo "✅ 환경 변수 변경 없음, build_runner 실행 생략"
fi

# 빌드 실행
if [ "$BUILD_TYPE" == "apk" ]; then
  echo "🚀 $FLAVOR 환경에서 APK 빌드 시작..."
  flutter pub get
  flutter build apk --flavor $FLAVOR --target=lib/main.dart

elif [ "$BUILD_TYPE" == "aab" ]; then
  echo "🚀 $FLAVOR 환경에서 AAB 빌드 시작..."
  flutter pub get
  flutter build appbundle --flavor $FLAVOR --target=lib/main.dart

elif [ "$BUILD_TYPE" == "ios" ]; then
  echo "🚀 $FLAVOR 환경에서 iOS 빌드 시작..."
  cd ios
  pod install  # iOS 종속성 설치
  cd ..
  flutter build ios --flavor $FLAVOR --target=lib/main.dart --no-codesign
  echo "🎉 iOS 빌드 완료! (Xcode에서 수동으로 서명 필요)"

else
  echo "❌ 올바른 빌드 타입을 입력하세요! (apk, aab, ios)"
  exit 1
fi

echo "🎉 빌드 완료!"