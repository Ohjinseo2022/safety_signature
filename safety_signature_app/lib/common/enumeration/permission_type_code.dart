enum PermissionTypeCode {
  CALENDARFULLACCESS('calendarFullAccess', "카메라권한"),
  NOTIFICATION('notification', "알림"),
  REMINDERS('reminders', "미리알림"),
  UNDEFINED("undefined", "");

  const PermissionTypeCode(this.code, this.displayName);
  final String code;
  final String displayName;
  factory PermissionTypeCode.getByCode(String code) {
    return PermissionTypeCode.values.firstWhere(
        (value) => code.contains(value.code),
        orElse: () => PermissionTypeCode.UNDEFINED);
  }
}
