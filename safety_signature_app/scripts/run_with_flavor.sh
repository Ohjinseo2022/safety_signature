#!/bin/bash

FLAVOR=$1  # 실행할 flavor 값

if [ -z "$FLAVOR" ]; then
  echo "❌ 실행할 flavor를 입력하세요! (예: local, staging, prod)"
  exit 1
fi

echo "🛠 실행 환경: $FLAVOR"

# 현재 환경 파일과 새로운 환경 파일이 다르면 변경
if ! cmp -s .env .env.$FLAVOR; then
  echo "🔄 환경 변수 변경 감지, .env 업데이트 중..."
  cp .env.$FLAVOR .env
  flutter pub run build_runner build --delete-conflicting-outputs
else
  echo "✅ 환경 변수 변경 없음, build_runner 실행 생략"
fi

echo "🚀 $FLAVOR 환경에서 Flutter 실행"
flutter run --flavor $FLAVOR --target=lib/main.dart