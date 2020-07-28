package com.example.lixc.enums;

/**
 * @className: PolicyEnum
 * @description: TODO
 * @Author: Wilson
 * @createTime 2020/7/22 22:28
 */
public enum PolicyTypeEnum {

    POLICY_TYPE_USE("use", "使用协议"),

    POLICY_TYPE_PRIVATE("private", "隐私协议");

    private String code;

    private String message;

    PolicyTypeEnum(String code, String message) {
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
