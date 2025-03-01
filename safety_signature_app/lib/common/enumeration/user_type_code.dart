enum UserTypeCode {
  MASTER_ADMINISTRATOR("MASTER_ADMINISTRATOR", "마스터 관리자"),
  CORPORATE_MANAGER("CORPORATE_MANAGER", "기업 관리자"),
  GENERAL_MEMBER("GENERAL_MEMBER", "일반 회원"),
  UNDEFINED("UNDEFINED", "");

  const UserTypeCode(this.code, this.displayName);
  final String code;
  final String displayName;
  factory UserTypeCode.getByCode(String code) {
    return UserTypeCode.values.firstWhere((value) => code.contains(value.code),
        orElse: () => UserTypeCode.UNDEFINED);
  }
}
