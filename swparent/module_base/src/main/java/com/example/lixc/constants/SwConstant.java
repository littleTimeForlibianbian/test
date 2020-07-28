package com.example.lixc.constants;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/7 11:13
 */
//常量设置类
public class SwConstant {

    //点赞活跃数量
    public static final int PRAISENUM = 10;
    //图片服务器访问地址
    public static final String DOMAIN = "http://192.168.244.129";

    //ftp 参数配置
    public static final String FTP_HOST = "192.168.244.129";
    public static final int FTP_PORT = 21;
    public static final String FTP_USERNAME = "ftpuser";
    public static final String FTP_PASSWORD = "slowwarm1234";
    //基本图片存储位置
    public static final String IMAGE_STORE_PATH = "/home/ftpuser/images/";
    public static final String IMAGE_SHOW_PATH = DOMAIN + "/images/";
    //    public static final String FTP_BASE_PATH = "/ftpuser/images/";
    //头像存储为位置
    public static final String FTP_BASE_HEAD_PATH = "/home/ftpuser/images/head/";


    public static void main(String[] args) throws IOException {
        FTPClient ftp = new FTPClient();
        int reply;
        ftp.connect("192.168.244.129", 21);// 连接FTP服务器
        // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
        ftp.login("ftpuser", "slowwarm1234");// 登录
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            System.out.println("建立连接失败");
        }
        if (ftp.changeWorkingDirectory("/home/ftpuser/images/")) {
            if (!ftp.makeDirectory("/home/ftpuser/images/20200724")) {
                System.out.println("建立目录失败");
            } else {
                System.out.println("建立目录成功");
                boolean b = ftp.changeWorkingDirectory("/home/ftpuser/images/20200724");
                System.out.println("切换目录：" + (b ? "true" : "false"));

            }

        } else {
            System.out.println("切换目录失败");
        }
    }
}
