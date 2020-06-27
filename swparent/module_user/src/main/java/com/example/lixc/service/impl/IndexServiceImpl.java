package com.example.lixc.service.impl;

import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.entity.*;
import com.example.lixc.enums.UserStatusEnum;
import com.example.lixc.enums.WorkStatusEnum;
import com.example.lixc.mapper.*;
import com.example.lixc.service.IndexService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.util.ThumbUtil;
import com.example.lixc.util.ToolsUtil;
import com.example.lixc.vo.back.WorkBack;
import com.example.lixc.vo.query.WorkQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
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

@Service
@Slf4j
public class IndexServiceImpl implements IndexService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAttrMapper userAttrMapper;

    @Autowired
    private SysImageMapper imageMapper;
    @Autowired
    private SysWorkMapper workMapper;

    @Autowired
    private SysWorkImageMapper workImageMapper;

    @Autowired
    private WFavoriteMapper wFavoriteMapper;

    @Autowired
    private SysDictMapper dictMapper;

    @Autowired
    private SysWorkDictMapper workDictMapper;

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


    @Override
    public ResultJson uploadWork(WorkQuery workQuery) {
        int loginUserId = SysConfigUtil.getLoginUserId();
        User user = userMapper.selectByPrimaryKey(loginUserId);
        if (user == null) {
            log.error("用戶{}不存在", loginUserId);
            return ResultJson.buildError("用戶不存在");
        }

        ResultJson resultJson = workQuery.checkBasicParams();
        if (!ToolsUtil.verifyParams(resultJson)) {
            return resultJson;
        }
        String styleLabelForAdd = workQuery.getStyleLabelForAdd();
        String categoryLabelForAdd = workQuery.getCategoryLabelForAdd();
        String[] split = styleLabelForAdd.split("-");
        if (split.length > SysConfigUtil.selectMaxWorkStyleLabelCount()) {
            return ResultJson.buildError("画风标签数量过多");
        }
        String[] split1 = categoryLabelForAdd.split("-");
        if (split1.length > SysConfigUtil.selectMaxWorkCategoryLabelCount()) {
            return ResultJson.buildError("品类标签数量过多");
        }
        SysWork work = new SysWork();
        work.setContent(workQuery.getContent());
        work.setIsDelete("N");
        work.setName(workQuery.getName());
        work.setStatus(WorkStatusEnum.WORK_STATUS_WAIT.getCode());
        work.setUserId(SysConfigUtil.getLoginUserId());
        work.setCreateBy(SysConfigUtil.getLoginUserId());
//        work.setUserId(0);
//        work.setCreateBy(0);
        work.setCreateTime(new Date());
        workMapper.insertUseGeneratedKeys(work);
        int workId = work.getId();
        //添加作品图片关联
        String ids = workQuery.getImageIds();
        List<SysWorkImage> list = new ArrayList<>();
        for (String id : ids.split("-")) {
            SysWorkImage workImage = new SysWorkImage();
            workImage.setImageId(Integer.valueOf(id));
            workImage.setWorkId(workId);
            list.add(workImage);
        }
        workImageMapper.insertList(list);
        //处理标签
        //添加作品标签关联表
        List<String> strings = Arrays.asList(split);
        List<String> strings1 = Arrays.asList(split1);
        strings.addAll(strings1);
        List<SysWorkDict> workDicts = new ArrayList<>();
        for (String str : strings) {
            SysWorkDict workDict = new SysWorkDict();
            workDict.setDictId(Integer.valueOf(str));
            workDict.setWorkId(workId);
            workDicts.add(workDict);
        }
        workDictMapper.insertList(workDicts);
        if (!workQuery.getIsNormal()) {
            user.setStatus(UserStatusEnum.USER_STATUS_STEP1.getCode());
            userMapper.updateByPrimaryKeySelective(user);
        }
        return ResultJson.buildSuccess("上传作品成功");
    }

    @Override
    public ResultJson selectAllWorkLabels() {
        return ResultJson.buildSuccess(dictMapper.selectAll());
    }

    /**
     * 查询首页的作品列表
     *
     * @param workQuery
     * @param more
     * @return
     */
    @Override
    public Page<WorkBack> workList(WorkQuery workQuery, String more) {
        PageHelper.startPage(workQuery.getPageNo(), workQuery.getPageSize());
        List<WorkBack> sysWorks = workMapper.selectForList(workQuery, more);
        return (Page<WorkBack>) sysWorks;
    }

    @Override
    public ResultJson workDetail(WorkQuery workQuery) {
        Map<String, Object> map = new HashMap<>();
        int workId = workQuery.getId();
        if (workId <= 0) {
            return ResultJson.buildError("传入id为空");
        }
        WorkBack sysWorkBack = workMapper.selectById(workId);
        if (sysWorkBack == null) {
            return ResultJson.buildError("查询作品详情失败，查询结果为空");
        }
        int userId = SysConfigUtil.getLoginUserId();
        //查询是否是喜欢此作品
        int count = wFavoriteMapper.selectCountByWorkId(userId, workId);
        sysWorkBack.setIsLike(count > 0);
        map.put("workBack", sysWorkBack);
        //查询该作者的其余作品
        Integer authorId = sysWorkBack.getUserId();
        WorkQuery query = new WorkQuery();
        query.setUserId(authorId);
        List<WorkBack> others = workMapper.selectForList(query, "Y");
        map.put("other", others);
        return ResultJson.buildSuccess(map);
    }

    @Override
    public ResultJson createHistory(String content) {
        //获取当前登录的用户
        int loginUserId = SysConfigUtil.getLoginUserId();
        User user = userMapper.selectByPrimaryKey(loginUserId);
        if (user == null) {
            log.error("用戶{}不存在", loginUserId);
            return ResultJson.buildError("用戶不存在");
        }
        UserAttr userAttr = userAttrMapper.selectByUserId(loginUserId);
        if (userAttr == null) {
            UserAttr attr = new UserAttr();
            attr.setUserId(loginUserId);
            attr.setCreateTime(new Date());
            attr.setUHistory(content);
            userAttrMapper.insertSelective(attr);
        } else {
            userAttr.setUHistory(content);
            userAttrMapper.updateByPrimaryKey(userAttr);
        }
        user.setStatus(UserStatusEnum.USER_STATUS_STEP2.getCode());
        userMapper.updateByPrimaryKey(user);
        return ResultJson.buildSuccess("创作过往填写成功");
    }

    @Override
    public ResultJson addWebsite(String website) {
        //获取当前登录的用户
        int loginUserId = SysConfigUtil.getLoginUserId();
        User user = userMapper.selectByPrimaryKey(loginUserId);
        if (user == null) {
            log.error("用戶{}不存在", loginUserId);
            return ResultJson.buildError("用戶不存在");
        }
        UserAttr userAttr = userAttrMapper.selectByUserId(loginUserId);
        if (userAttr == null) {
            UserAttr attr = new UserAttr();
            attr.setUserId(loginUserId);
            attr.setCreateTime(new Date());
            attr.setWebsite(website);
            userAttrMapper.insertSelective(attr);
        } else {
            userAttr.setWebsite(website);
            userAttrMapper.updateByPrimaryKey(userAttr);
        }
        user.setStatus(UserStatusEnum.USER_STATUS_STEP3.getCode());
        userMapper.updateByPrimaryKey(user);
        return ResultJson.buildSuccess("常用网站填写成功");
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