package com.example.lixc.exception;

/**
 * Copyright: Copyright (c) 2019 LanRu-Caifu
 *
 * @ClassName: SmsException.java
 * @Description: 发送短信接口异常
 * @version: v1.0.0
 * @author: wen_guoxing
 * @date: 2019年5月9日 下午6:44:24
 */
public class SmsException extends Exception {
    private static final long serialVersionUID = 1L;

    public SmsException() {
        super();
    }

    public SmsException(String msg) {
        super(msg);
    }

    public SmsException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public SmsException(Throwable throwable) {
        super(throwable);
    }
}
