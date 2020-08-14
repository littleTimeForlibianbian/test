package com.example.lixc.enums;

import lombok.Getter;

/**
 * @className: UserSecurityEnum
 * @description: TODO
 * @Author: Wilson
 * @createTime 2020/8/4 23:15
 */
@Getter
public enum UserSecurityEnum {

    USER_NEED_AUTHORITIES(201, "用户未登录"),
    USER_LOGIN_FAILED(202, "用户账号或密码错误"),
    USER_LOGIN_SUCCESS(203, "用户登录成功"),
    USER_NO_ACCESS(204, "用户无权访问"),
    USER_LOGOUT_SUCCESS(205, "用户登出成功"),
    TOKEN_IS_BLACKLIST(206, "此token为黑名单"),
    LOGIN_IS_OVERDUE(207, "登录已失效");

    private Integer code;

    private String message;

    UserSecurityEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @author: zzx
     * @date: 2018-10-15 16:26
     * @deprecation:通过code返回枚举
     */
    public static UserSecurityEnum parse(int code) {
        UserSecurityEnum[] values = values();
        for (UserSecurityEnum value : values) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new RuntimeException("Unknown code of ResultEnum");
    }
}
