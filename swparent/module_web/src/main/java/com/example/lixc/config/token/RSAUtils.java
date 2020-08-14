package com.example.lixc.config.token;

import java.security.*;

/**
 * @className: RSAUtils
 * @description: RSA算法工具类
 * @Author: Wilson
 * @createTime 2020/8/4 23:39
 */
public class RSAUtils {

    private static KeyPair keyPair = initKeyPair();
    private static boolean flag = false;

    //读取证书文件获取证书的公私钥
    public static KeyPair initKeyPair() {
        if (flag) {
            return keyPair;
        }
        try {
            KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
            rsa.initialize(2048);
            flag = true;
            return rsa.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static KeyPair getKeyPair() {
        return keyPair;
    }
}
