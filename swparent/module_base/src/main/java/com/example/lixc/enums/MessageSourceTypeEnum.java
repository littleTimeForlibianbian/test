package com.example.lixc.enums;

/**
 * @className: WorkSourceTypeEnum
 * @description: TODO 消息来源类型枚举类
 * @Author: Wilson
 * @createTime 2020/7/20 14:08
 */
public enum MessageSourceTypeEnum {
    message_type_work("work", "作品"),
    message_type_comment("comment", "评论"),
    message_type_user("user", "用户");


    private String code;
    private String message;

    MessageSourceTypeEnum(String code, String message) {
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
