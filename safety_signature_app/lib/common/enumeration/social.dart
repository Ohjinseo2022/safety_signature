enum SocialTypeCode {
  GOOGLE('google', '구글'),
  KAKAO('kakao', '카카오'),
  NAVER('naver', '네이버'),
  EMAIL('email', '이메일'),
  UNDEFINED('undefined', '');

  const SocialTypeCode(this.code, this.displayName);
  final String code;
  final String displayName;
  factory SocialTypeCode.getByCode(String code) {
    return SocialTypeCode.values.firstWhere((value) => value.code == code,
        orElse: () => SocialTypeCode.UNDEFINED);
  }
}
