package com.example.lixc.enums;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/1 15:52
 */
public enum MessageActionEnum {
    MESSAGE_ACTION_COMMENT("comment", "评论"),

    MESSAGE_ACTION_PRAISE("praise", "赞"),

    MESSAGE_ACTION_REPLY("reply", "回复"),

    MESSAGE_ACTION_RECOMMEND("recommend", "推荐"),

    MESSAGE_ACTION_FOCUS("focus", "关注"),
    //发布新作品动作
    MESSAGE_ACTION_PUBLISH("publish", "发布"),

    MESSAGE_ACTION_MESSAGE("message", "系统消息");

    private String code;
    private String message;

    MessageActionEnum(String code, String message) {
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
