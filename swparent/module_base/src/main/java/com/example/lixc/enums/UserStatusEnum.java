package com.example.lixc.enums;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/7 12:05
 */
public enum UserStatusEnum {
    USER_STATUS_REG(1, "注册"),
    USER_STATUS_ACTIVE(2, "激活"),
    USER_STATUS_STEP1(3, "已完成作品上传"),
    USER_STATUS_STEP2(4, "已填写创作过往"),
    USER_STATUS_STEP3(5, "已填写常用网站");


    private int code;
    private String message;

    UserStatusEnum(int code, String message) {
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
