package com.example.lixc.enums;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/21 9:46
 */
public enum WorkStatusEnum {

    WORK_STATUS_WAIT(1, "待审核"),
    WORK_STATUS_PASS(2, "审核通过"),
    WORK_STATUS_FAIL(3, "审核拒绝");

    private int code;
    private String message;

    WorkStatusEnum(int code, String message) {
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
