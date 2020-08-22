package com.example.lixc;

import java.util.Base64;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/7 12:31
 */
public class Test {

    public static void main(String[] args) {
        String message = "test message";
        String result = Base64.getEncoder().encodeToString(message.getBytes());
        System.out.println(result);
        String origin = new String(Base64.getDecoder().decode(result.getBytes()));
        System.out.println(origin);
        System.out.println(origin.equals(message));
    }

}
