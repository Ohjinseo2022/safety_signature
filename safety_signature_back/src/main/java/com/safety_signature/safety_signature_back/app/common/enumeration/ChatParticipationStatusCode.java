package com.safety_signature.safety_signature_back.app.common.enumeration;

/**
 * 채팅에 참여한 회원들의 상태코드를 정의
 */
public enum ChatParticipationStatusCode {
    // 채팅방에 참여 또는 초대 받았으나 한번도 채팅입력 or 채팅방 입장을 하지 않은상태.
    CHAT_PENDING("대기중"),
    // 채팅방에 참여하여 채팅참여가 가능한 상태. -> 웹소켓연결. 상태일땐 엑티브
    CHAT_ACTIVE("활성"),
    // 채팅방에 참여중이지만, 소켓연결은 끊킴... 앱 알림발송이 필요한겅우!
    CHAT_NOTIFICATION_PENDING("알림 대기"),
    // 채팅참여중 사용자가 직접 채팅방을 나간경우
    CHAT_LEFT("나가기"),
    // 관리자, 또는 채팅방 방장에게 추방 또는 제제 를 당한 상태
    CHAT_RESTRICTED("채팅 제한");
    private String value;
    ChatParticipationStatusCode(String value) {this.value = value;}
    public String getValue() {return value;}

}
