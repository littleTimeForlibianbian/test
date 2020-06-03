package com.example.lixc.exception;

/**
 * 
* Copyright: Copyright (c) 2019 LanRu-Caifu
* 
* @ClassName: URLException.java
* @Description: URL相关错误
*
* @version: v1.0.0
* @author: wen_guoxing
* @date: 2019年5月9日 下午6:44:24 
*
 */
public class URLException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public URLException() {
        super();
    }
    public URLException(String msg) {
        super(msg);
    }
    public URLException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
    public URLException(Throwable throwable) {
        super(throwable);
    }
}
