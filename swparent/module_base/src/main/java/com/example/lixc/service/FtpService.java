package com.example.lixc.service;

import com.example.lixc.util.ResultJson;
import org.springframework.web.multipart.MultipartFile;

/**
 * @className: FtpService
 * @description: ftp 文件上传service，业务相关service调用此service
 * @Author: Wilson
 * @createTime 2020/7/26 22:33
 */
public interface FtpService {

    /**
     * 上传文件到 文件服务器
     *
     * @param base64    图片base64
     * @param type      head:头像图片  normal 普通作品
     * @param withThumb 是否上传缩略图
     * @return true ： 成功  false：失败
     */
    ResultJson uploadToServer(String base64, boolean withThumb);

    ResultJson uploadToServer(MultipartFile files, boolean withThumb);
}
