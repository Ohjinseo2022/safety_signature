enum UserStatusCode {
  PENDING("PENDING", "계정 신청중"),
  ACTIVE("ACTIVE", "계정 사용중"),
  DORMANT("DORMANT", "계정 휴면중"),
  QUIT("QUIT", "계정 탈퇴중"),
  WITHDRAWAL("WITHDRAWAL", "계정 탈퇴"),
  LOCKED("LOCKED", "계정 잠김"),
  REJECT_BECOME("REJECT_BECOME", "가입 거부"),
  RETRY_PENDING("RETRY_PENDING", "계정 재신청중"),
  IMPOSSIBLE_BECOME("IMPOSSIBLE_BECOME", "가입 불가"),
  UNDEFINED('undefined', '');

  const UserStatusCode(this.code, this.displayName);
  final String code;
  final String displayName;
  factory UserStatusCode.getByCode(String code) {
    return UserStatusCode.values.firstWhere((value) => value.code == code,
        orElse: () => UserStatusCode.UNDEFINED);
  }
}
