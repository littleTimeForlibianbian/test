package com.example.lixc.enums;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/7 12:05
 */
public enum UserStatusEnum {
    /**
     * 1.已注册
     */
    USER_STATUS_REG(1, "已注册"),
    USER_STATUS_ACTIVE(2, "已激活"),
    USER_STATUS_CHOOSE(3, "已选择标签"),
    USER_STATUS_APPLY(4, "已申请"),
    USER_STATUS_PASS(5, "审核通过"),
    USER_STATUS_FAIL(6, "审核拒绝");


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
