package com.example.lixc.enums;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/7 23:17
 */
public enum UserVoiceTypeEnum {
    TYPE_VERSIONSPO("spo", "版本剧透"),

    TYPE_FEEDBACKOPT("opt", "反馈优化");

    private String code;
    private String message;

    UserVoiceTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
