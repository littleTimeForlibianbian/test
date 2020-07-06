package com.example.lixc.enums;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/1 15:46
 */
public enum MessageTypeEnum {
    MESSAGE_TYPE_ANN(1, "公告"),
    MESSAGE_TYPE_REM(2, "提醒"),
    MESSAGE_TYPE_MES(3, "私信");
    private int code;
    private String message;

    MessageTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
