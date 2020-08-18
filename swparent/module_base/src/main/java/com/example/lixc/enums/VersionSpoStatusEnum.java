package com.example.lixc.enums;

/**
 * @className: VersionSpoStatusEnum
 * @description: TODO
 * @Author: Wilson
 * @createTime 2020/8/17 10:55
 */
public enum VersionSpoStatusEnum {
    /**
     * 1.已注册
     */
    SPO_STATUS_APPLY(1, "待优化"),
    SPO_STATUS_IN  (2, "优化中"),
    SPO_STATUS_END(3, "优化完毕");


    private int code;
    private String message;

    VersionSpoStatusEnum(int code, String message) {
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
