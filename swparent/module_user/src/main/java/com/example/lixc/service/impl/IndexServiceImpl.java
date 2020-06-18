package com.example.lixc.service.impl;

import com.example.lixc.entity.SysImage;
import com.example.lixc.mapper.SysImageMapper;
import com.example.lixc.service.IndexService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.util.ThumbUtil;
import com.example.lixc.util.ToolsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

@Service
public class IndexServiceImpl implements IndexService {


    @Autowired
    private SysImageMapper imageMapper;

    @Value("${sw.work.url}")
    private String url;

    @Value("${sw.work.thumbRatio}")
    private float ratio;

    @Override
    @Transactional
    public ResultJson uploadImage(MultipartFile[] files) {
        if (files.length <= 0) return ResultJson.buildError("请选择需要上传的文件！");
        if (files.length > 4) return ResultJson.buildError("文件数量过多！");
        String result = checkFileParams(files);
        if (!StringUtils.isEmpty(result)) {
            return ResultJson.buildError(result);
        }
        List<SysImage> imageList = new ArrayList<>();
        for (MultipartFile file : files) {
            String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String newFileName = new Date().getTime() + "_" + new Random(10000).nextInt();
            String thumbFileName = newFileName + "_thumb";
            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            File f = new File(url + sdf.format(currentDate));
            if (!f.exists()) {
                f.mkdir();
            }
            File targetFile = new File(f, newFileName + suffix);
            File thumbFile = new File(f, thumbFileName + suffix);
            try {
                file.transferTo(targetFile);
                ThumbUtil.proportionalScalingWithNoWater(targetFile, thumbFile, ratio);
                SysImage image = new SysImage();
                image.setUrl(targetFile.getAbsolutePath());
                image.setThumbUrl(thumbFile.getAbsolutePath());
                imageMapper.insertUseGeneratedKeys(image);
                imageList.add(image);
            } catch (IOException e) {
                e.printStackTrace();
                //删除文件
                targetFile.deleteOnExit();
                thumbFile.deleteOnExit();
                return ResultJson.buildError("上传图片异常");
            }
        }
        return ResultJson.buildSuccess(imageList, "图片上传成功");
    }

    /**
     * 检查图片格式和大小
     *
     * @return
     */
    private String checkFileParams(MultipartFile[] files) {
        String result = null;
        if (files.length > 0) {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                if (!StringUtils.isEmpty(fileName)) {
                    //验证图片后缀
                    String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                    if (!ToolsUtil.regexPicture(suffix)) {
                        result = "图片格式不符合规范，请重新上传";
                        break;
                    }
                    if (file.getSize() > 1024 * 2) {
                        result = "图头大小不符合规范，请重新上传";
                        break;
                    }
                }
            }
        }
        return result;
    }
}