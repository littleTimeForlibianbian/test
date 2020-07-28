package com.example.lixc.service.impl;

import com.example.lixc.constants.SwConstant;
import com.example.lixc.entity.SysImage;
import com.example.lixc.service.FtpService;
import com.example.lixc.util.BASE64DecodedMultipartFileUtil;
import com.example.lixc.util.FtpUtil;
import com.example.lixc.util.ResultJson;
import com.example.lixc.util.ToolsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @className: FtpServiceImpl
 * @description: TODO
 * @Author: Wilson
 * @createTime 2020/7/26 22:37
 */
@Service
@Slf4j
public class FtpServiceImpl implements FtpService {

    @Override
    public ResultJson uploadToServer(String base64, boolean withThumb) {
        //转换为file
        MultipartFile multipartFile = BASE64DecodedMultipartFileUtil.base64ToMultipart(base64);
        String suffix = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        //获取文件夹的名字
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //判断基础路径
//        String basePath = type == null || type.equalsIgnoreCase("normal") ? SwConstant.FTP_BASE_PATH : SwConstant.FTP_BASE_HEAD_PATH;
        String filePath = sdf.format(currentDate);
        String fileName = ToolsUtil.getCode() + suffix;
        String thumb_FileName = null;
        Map<String, String> map = new HashMap<>();
        try {
            //上传图片
            boolean b = FtpUtil.uploadFile(SwConstant.IMAGE_STORE_PATH, filePath, fileName, multipartFile.getInputStream());
            if (b) {
                if (withThumb) {
                    thumb_FileName = ToolsUtil.getCode() + "_thumb" + suffix;
                    FtpUtil.uploadFile(SwConstant.IMAGE_STORE_PATH, filePath, thumb_FileName, multipartFile.getInputStream());
                    map.put("thumbUrl", SwConstant.IMAGE_SHOW_PATH + filePath + "/" + thumb_FileName);
                }
                map.put("result", "true");
                //确定数据库中存储的字段，根据nginx的路径映射进行确定 /images/=>  /home/ftpuser/images/
                map.put("url", SwConstant.IMAGE_SHOW_PATH + filePath + "/" + fileName);
                return ResultJson.buildSuccess(map);
            } else {
                return ResultJson.buildError("上传图片失败");
            }

        } catch (IOException e) {
            log.error("上传图片异常：" + e.getLocalizedMessage());
            return ResultJson.buildError("上传图片异常");
        }

    }

    @Override
    public ResultJson uploadToServer(MultipartFile multipartFile, boolean withThumb) {
        String suffix = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        //获取文件夹的名字
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //判断基础路径
//        String basePath = type == null || type.equalsIgnoreCase("normal") ? SwConstant.FTP_BASE_PATH : SwConstant.FTP_BASE_HEAD_PATH;
        String filePath = sdf.format(currentDate);
        String fileName = ToolsUtil.getCode() + suffix;
        String thumb_FileName = null;
        Map<String, String> map = new HashMap<>();
        try {
            //上传图片
            boolean b = FtpUtil.uploadFile(SwConstant.IMAGE_STORE_PATH, filePath, fileName, multipartFile.getInputStream());
            if (b) {
                if (withThumb) {
                    thumb_FileName = ToolsUtil.getCode() + "_thumb" + suffix;
                    FtpUtil.uploadFile(SwConstant.IMAGE_STORE_PATH, filePath, thumb_FileName, multipartFile.getInputStream());
                    map.put("thumbUrl", SwConstant.IMAGE_SHOW_PATH + filePath + File.separator + thumb_FileName);
                }
                map.put("result", "true");
                //确定数据库中存储的字段，根据nginx的路径映射进行确定 /images/=>  /home/ftpuser/images/
                map.put("url", SwConstant.IMAGE_SHOW_PATH + filePath + File.separator + fileName);
                return ResultJson.buildSuccess(map);
            } else {
                return ResultJson.buildError("上传图片失败");
            }

        } catch (IOException e) {
            log.error("上传图片异常：" + e.getLocalizedMessage());
            return ResultJson.buildError("上传图片异常");
        }

    }
}
