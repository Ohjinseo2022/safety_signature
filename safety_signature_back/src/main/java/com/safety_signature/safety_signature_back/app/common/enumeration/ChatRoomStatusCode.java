package com.safety_signature.safety_signature_back.app.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 채팅방 상태코드
 */
public enum ChatRoomStatusCode {
    IDLE("대기중"),// 처음 생성되고 채팅메세지가 1개도 없는 상태
    ACTIVE("활성"),// 채팅을 시작하고난 뒤 상태
    INACTIVE("비활성");// 모든 참여자가 나간 상태
    private String value;
    ChatRoomStatusCode(String value) {this.value=value;}
    public String getValue() {return value;}
}
