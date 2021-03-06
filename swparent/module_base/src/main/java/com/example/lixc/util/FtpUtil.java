package com.example.lixc.util;

import com.example.lixc.constants.SwConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @className: FtpUtil
 * @description: 文件上传工具类  將文件上传到文件服务器
 * @Author: Wilson
 * @createTime 2020/7/26 9:17
 */
@Slf4j
public class FtpUtil {

    /**
     * 使用默认的配置进行文件上传
     *
     * @param basePath    FTP服务器基础目录,/home/ftpuser/images
     * @param filePath    FTP服务器文件存放路径。例如分日期存放：/2018/05/28。文件的路径为basePath+filePath
     * @param fileName    上传到FTP服务器上的文件名
     * @param inputStream 上传到FTP服务器上的文件名
     * @return boolean  true 上传成功 false  上传失败
     */
    public static boolean uploadFile(String basePath, String filePath, String fileName, InputStream inputStream) {
        return uploadFile(SwConstant.FTP_HOST, SwConstant.FTP_PORT, SwConstant.FTP_USERNAME, SwConstant.FTP_PASSWORD,
                basePath, filePath, fileName, inputStream);
    }

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param host     FTP服务器ip
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录,/home/ftpuser/images
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2018/05/28。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(
            String host,
            int port,
            String username,
            String password,
            String basePath,
            String filePath,
            String filename,
            InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录basePath=> /home/ftpuser/images/
            //切换到上传目录filePath=> 20200727
            if (!ftp.changeWorkingDirectory(basePath + filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += dir;

                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //设置为被动模式
            ftp.enterLocalPassiveMode();
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }


    public static boolean deleteFile(String filePath, String host, int port, String username, String password) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            if (ftp.changeWorkingDirectory(new File(filePath).getParent())) {
                ftp.deleteFile(filePath);
            } else {
                log.error("进入目录失败，请检查目录是否存在:{}", filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean deleteFile(String filePath) {
        return deleteFile(filePath, SwConstant.FTP_HOST, SwConstant.FTP_PORT, SwConstant.FTP_USERNAME, SwConstant.FTP_PASSWORD);
    }

    public static void main(String[] args) {
        String imageStorePath = SwConstant.IMAGE_STORE_PATH;
        String url = "http://192.168.244.129/images/20200728/737622379743809536.jpeg";
//        String[] split = url.split("/");
        String fileName = url.substring(url.lastIndexOf("/") - 8, url.length());
        imageStorePath += fileName.split("/")[0];
        deleteFile(imageStorePath + fileName.split("/")[1]);
    }

}