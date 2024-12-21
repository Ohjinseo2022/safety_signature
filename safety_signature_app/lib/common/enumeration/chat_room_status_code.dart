enum ChatRoomStatusCode {
  IDLE("IDLE", "대기중"), // 처음 생성되고 채팅메세지가 1개도 없는 상태
  ACTIVE("ACTIVE", "활성"), // 채팅을 시작하고난 뒤 상태
  INACTIVE("INACTIVE", "비활성"), // 모든 참여자가 나간 상태
  UNDEFINED('undefined', '');

  const ChatRoomStatusCode(this.code, this.displayName);
  final String code;
  final String displayName;
  factory ChatRoomStatusCode.getByCode(String code) {
    return ChatRoomStatusCode.values.firstWhere((value) => value.code == code,
        orElse: () => ChatRoomStatusCode.UNDEFINED);
  }
}
