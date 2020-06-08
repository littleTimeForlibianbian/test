package com.example.lixc.enums;

import lombok.Data;
import lombok.ToString;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/8 15:43
 */
public enum BaseStatusEnum {

    STATUS_ENABLE("Y", "已开启"),
    STATUS_DISABLE("N", "已停用");

    private String code;
    private String message;

    BaseStatusEnum(String code, String message) {
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