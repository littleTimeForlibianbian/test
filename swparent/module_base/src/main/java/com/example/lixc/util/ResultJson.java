package com.example.lixc.util;


import com.example.lixc.enums.ResultJsonEnum;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 功能描述：工具类
 */
@Data
@ToString
public class ResultJson implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer status; // 状态码 200 表示成功，400表示失败
    private Object data; // 数据
    private String message;// 描述

    public ResultJson() {
    }

    public ResultJson(Integer code, Object data, String msg) {
        this.status = code;
        this.data = data;
        if (msg == null) {
            if (ResultJsonEnum.SUCCESS_CODE.getCode() == code) {
                this.message = ResultJsonEnum.SUCCESS_CODE.getName();
            } else if (ResultJsonEnum.ERROR_CODE.getCode() == code) {
                this.message = ResultJsonEnum.ERROR_CODE.getName();
            } else if (ResultJsonEnum.LOGINOUT_CODE.getCode() == code) {
                this.message = ResultJsonEnum.LOGINOUT_CODE.getName();
            } else {
                this.message = "未知错误";
            }

        } else {

            this.message = msg;
        }
    }

    // 成功，传入数据
    public static ResultJson buildSuccess() {
        return new ResultJson(ResultJsonEnum.SUCCESS_CODE.getCode(), null, ResultJsonEnum.SUCCESS_CODE.getName());
    }

    // 成功，传入数据
    public static ResultJson buildSuccess(String msg) {
        return new ResultJson(ResultJsonEnum.SUCCESS_CODE.getCode(), null, msg);
    }

    // 成功，传入数据
    public static ResultJson buildSuccess(Object data) {
        return new ResultJson(ResultJsonEnum.SUCCESS_CODE.getCode(), data, null);
    }

    // 失败，传入描述信息
    public static ResultJson buildError(String msg) {
        return new ResultJson(ResultJsonEnum.ERROR_CODE.getCode(), null, msg);
    }

    // 失败，传入描述信息,状态码
    public static ResultJson buildError(String msg, Integer code) {
        return new ResultJson(code, null, msg);
    }

    // 必须登录，登录超时
    public static ResultJson buildErrorLogOut() {
        return new ResultJson(ResultJsonEnum.LOGINOUT_CODE.getCode(), null, ResultJsonEnum.LOGINOUT_CODE.getName());
    }

    // 成功，传入数据,及描述信息
    public static ResultJson buildSuccess(Object data, String msg) {
        return new ResultJson(ResultJsonEnum.SUCCESS_CODE.getCode(), data, msg);
    }

    // 成功，传入数据,及状态码
    public static ResultJson buildSuccess(Object data, int code) {
        return new ResultJson(code, data, null);
    }


}
