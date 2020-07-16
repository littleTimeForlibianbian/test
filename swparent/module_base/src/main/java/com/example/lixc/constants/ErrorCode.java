package com.example.lixc.constants;

/**
 * @Description 统一错误编码
 */
public class ErrorCode {
    public static final String SYSTEM_ERROR = "100000";

    /**
     * aip错误信息
     */
    //实名认证失败
    public static final int AUTH_FAIL = 100001;
    public static final int DOCTOR_CALLBACK_FAIL = 100002;
    public static final int PARAMS_FAIL = 100003;
    public static final int HOSPITAL_FAIL = 100004;
    public static final int DOCTOR_FAIL = 100005;
    public static final int CERT_FAIL = 100006;
    public static final int CERT_EXPIRE_FAIL = 100007;
    public static final int DOCTOR_SEAL_FAIL = 100008;
    public static final int DOCTOR_SEAL_EXPIRE_FAIL = 100009;
    public static final int HOSPITAL_SIGN_FAIL = 100010;
    public static final int CERT_NOT_BUY = 300;
}
