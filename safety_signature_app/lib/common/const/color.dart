import 'package:flutter/material.dart';

// (1) 클래식 테마 (차분하고 고급스러운 느낌)
// Primary: #3C4359
// Secondary: #6D758D (밝은 그레이-블루)
// Accent: #F4B400 (따뜻한 골드)
// Background: #F7F7F9 (밝은 오프 화이트)
// Text: #FFFFFF (밝은 텍스트) 또는 #1A1A1A (어두운 텍스트)
// (2) 모던 테마 (대비와 활기)
// Primary: #3C4359
// Secondary: #A1B3C9 (부드러운 하늘색)
// Accent: #E63946 (대비되는 레드)
// Background: #FFFFFF (순백)
// Text: #2E2E2E (진한 그레이)
// (3) 대비 테마 (강렬한 대비)
// Primary: #3C4359
// Secondary: #FF8C42 (따뜻한 오렌지)
// Accent: #1FAB89 (생동감 있는 민트 그린)
// Background: #F0F4F8 (밝은 블루 틴트 화이트)
// Text: #101820 (짙은 블랙)
// (4) 다크 모드 테마
// Primary: #3C4359
// Secondary: #585E73 (중간 톤 블루-그레이)
// Accent: #FFD700 (밝은 골드)
// Background: #1B1D26 (어두운 블루-블랙)
// Text: #FFFFFF (밝은 화이트)
const PRIMARY_COLOR = Color(0xff3C4359);
const BACK_GROUND_COLOR = Color(0xffA1B3C9);
const ACCENT_COLOR = Color(0xffE63946);
const SECONDARY_COLOR = Color(0xffFFFFFF);
const TEXT_COLOR = Color(0xff2E2E2E);

const GOOGLE_PRIMARY_COLOR = Color(0xffF2F2F2);
const NAVER_PRIMARY_COLOR = Color(0xff03c75a);
const KAKAO_CONTAINER_COLOR = Color(0xffFEE500);
const KAKAO_SYMBOL_COLOR = Color.fromRGBO(0, 0, 0, 0.9);
const KAKAO_LABEL_COLOR = Color(0xff191919);

const defaultTextStyle = TextStyle(
  fontSize: 16,
  fontWeight: FontWeight.w500,
  color: TEXT_COLOR,
);
