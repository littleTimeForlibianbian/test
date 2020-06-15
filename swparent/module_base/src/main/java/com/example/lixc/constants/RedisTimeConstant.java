package com.example.lixc.constants;


public class RedisTimeConstant {

    /**
     * 缓存时间
     * 5分钟
     */
    public final static int CACHE_5_MINUTE = 300;

    /**
     * 缓存时间
     * 1分钟
     */
    public final static int CACHE_1_MINUTE = 60;


    /**
     * 公共前缀
     */
    public final static String _PREFIX = "SW:";
    /**
     *
     */
    public static final String EMAIL_CODE_RANDOM = _PREFIX + "email_code_random_";
    /**
     * 短信配置
     */
    public final static String SYS_CONFIG_SMS = _PREFIX + "SMS_CONFIG";
    /**
     * 实名个人配置
     */
    public final static String SYS_CONFIG_AUTH_PERSION = _PREFIX + "AUTH_PERSION_CONFIG";
    /**
     * 短信模板配置——验证码
     */
    public final static String SYS_SMS_TEMPLATE_CODE = _PREFIX + "SYS_SMS_TEMPLATE:CODE";

    /**
     * 短信模板配置——验证码
     */
    public final static String SYS_SMS_TEMPLATE_URL_LOGIN = _PREFIX + "SYS_SMS_TEMPLATE:URL_LOGIN";

    /**
     * 邮件模板配置——验证码
     */
    public final static String SYS_EMAIL_TEMPLATE_CODE = _PREFIX + "SYS_EMAIL_TEMPLATE:CODE";

    /**
     * 邮件模板配置——验证码
     */
    public final static String SYS_EMAIL_TEMPLATE_URL_LOGIN = _PREFIX + "SYS_EMAIL_TEMPLATE:URL_LOGIN";

    /**
     * 同步项目信息，分布式锁key
     */
    public final static String SYNC_PROJECT_LOCK = _PREFIX + "SYNC:PROJECT_LOCK";

    /**
     * 系统配置 redis前缀，比如使用实名服务配置，短信配置等信息
     */
    public static final String SYS_CONFIG_TYPE = _PREFIX + "SYS_CONFIG:TYPE_";

    /**
     * 把所有产品都缓存到缓存
     */
    public static final String SYS_CONFIG_PRODUCT_ALL = _PREFIX + "SYS_CONFIG:PRODUCT_ALL";

    /**
     * 医务人员审核回调失败数据 五分钟 key
     */
    public static final String DOCTOR_FALL_CALLBACK_MINUTE = _PREFIX + "DOCTOR_FALL_CALLBACK_MINUTE:";

    /**
     * 医务人员审核回调失败数据 24小时 key
     */
    public static final String DOCTOR_FALL_CALLBACK_HOUR = _PREFIX + "DOCTOR_FALL_CALLBACK_HOUR:";

    /**
     * 医务人员签名数据回调失败数据 五分钟 key
     */
    public static final String MEDICALDATA_FALL_CALLBACK_MINUTE = _PREFIX + "MEDICALDATA_FALL_CALLBACK_MINUTE:";

    /**
     * 医务人员签名数据回调失败数据 24小时 key
     */
    public static final String MEDICALDATA_FALL_CALLBACK_HOUR = _PREFIX + "MEDICALDATA_FALL_CALLBACK_HOUR:";
}
