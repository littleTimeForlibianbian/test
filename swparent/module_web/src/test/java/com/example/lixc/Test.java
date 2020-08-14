package com.example.lixc;

import java.text.MessageFormat;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/7 10:16
 */
public class Test {
    public static void main(String[] args) {
        String str = "<html> <body> <p class=\"text-center\"> 验证地址：<a href=\"{}\">{}<</a></p> </body></html>";
//        System.out.println(MessageFormat.format(str, "123", "www.baidu.com", "www.baidu.com"));
        String str1 = "欢迎你：{0}";
        System.out.println(MessageFormat.format(str1, new String("123")));


    }
}
