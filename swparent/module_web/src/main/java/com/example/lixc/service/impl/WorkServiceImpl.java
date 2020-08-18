package com.example.lixc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.lixc.config.InitConfig;
import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.constants.SwConstant;
import com.example.lixc.entity.*;
import com.example.lixc.enums.*;
import com.example.lixc.mapper.*;
import com.example.lixc.service.FtpService;
import com.example.lixc.service.MessageService;
import com.example.lixc.service.WorkService;
import com.example.lixc.template.SimpleMessageTemplate;
import com.example.lixc.util.*;
import com.example.lixc.vo.back.CommentBack;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.back.WorkBack;
import com.example.lixc.vo.query.MessageQuery;
import com.example.lixc.vo.query.UserQuery;
import com.example.lixc.vo.query.WCommentQuery;
import com.example.lixc.vo.query.WorkQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class WorkServiceImpl implements WorkService {

    @Autowired
    private MessageService messageService;
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


    @Autowired
    private SysCommentMapper commentMapper;
    @Autowired
    private UFocusMapper uFocusMapper;

    @Autowired
    private SysReportMapper reportMapper;

    @Autowired
    private SysReportRecordMapper reportRecordMapper;

    @Autowired
    private FtpService ftpService;

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
            log.error(result);
            return ResultJson.buildError(result);
        }
        List<SysImage> imageList = new ArrayList<>();
        for (MultipartFile file : files) {
            ResultJson resultJson = ftpService.uploadToServer(file, true);
            if (ToolsUtil.verifyParams(resultJson)) {
//            List<SysImage> imageList = new ArrayList<>();
                Map<String, String> map = (Map<String, String>) resultJson.getData();
                SysImage image = new SysImage();
                image.setUrl(map.get("url"));
                image.setThumbUrl(map.get("thumbUrl"));
                image.setCreateTime(new Date());
                imageMapper.insertUseGeneratedKeys(image);
//            imageList.add(image);
                return ResultJson.buildSuccess(image);
            } else {
                return resultJson;
            }
        }
        return ResultJson.buildSuccess(imageList, "图片上传成功");
    }

    @Override
    public ResultJson uploadImageBase64(String data) {
        if (StringUtils.isEmpty(data)) {
            return ResultJson.buildError("传入图片为空");
        }
        //解码成字节数组
        String header = data.split(",")[0];
        String suffix = header.substring(header.indexOf("/") + 1, header.indexOf(";"));
        String imageData = data.split(",")[1];
        byte[] decode = Base64.getDecoder().decode(imageData);
        String newFileName = new Date().getTime() + "_" + new Random().nextInt(10000);
        String thumbFileName = newFileName + "_thumb";
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        File f = new File(url + sdf.format(currentDate));
        if (!f.exists()) {
            f.mkdir();
        }
        File targetFile = new File(f, newFileName + suffix);
        File thumbFile = new File(f, thumbFileName + suffix);
        //字节数组转成文件
        List<SysImage> imageList = new ArrayList<>();
        try {
            FileOutputStream fos = new FileOutputStream(targetFile);
            fos.write(decode);
            ThumbUtil.proportionalScalingWithNoWater(targetFile, thumbFile, ratio);
            SysImage image = new SysImage();
            image.setUrl(targetFile.getAbsolutePath());
            image.setThumbUrl(thumbFile.getAbsolutePath());
            image.setCreateTime(new Date());
            imageMapper.insertUseGeneratedKeys(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultJson.buildSuccess(imageList, "图片上传成功");
    }

    @Override
    public ResultJson uploadImageToServer(String base64) {
        if (StringUtils.isEmpty(base64)) {
            log.error("传入图片为空");
            return ResultJson.buildError("传入图片为空");
        }
        ResultJson resultJson = ftpService.uploadToServer(base64, true);
        if (ToolsUtil.verifyParams(resultJson)) {
//            List<SysImage> imageList = new ArrayList<>();
            Map<String, String> map = (Map<String, String>) resultJson.getData();
            SysImage image = new SysImage();
            image.setUrl(map.get("url"));
            image.setThumbUrl(map.get("thumbUrl"));
            image.setCreateTime(new Date());
            imageMapper.insertUseGeneratedKeys(image);
//            imageList.add(image);
            return ResultJson.buildSuccess(image);
        } else {
            return resultJson;
        }
    }


    @Override
    @Transactional
    public ResultJson uploadWork(WorkQuery workQuery) {
        int loginUserId = SysConfigUtil.getLoginUserId();
        User user = userMapper.selectByPrimaryKey(loginUserId);
        if (user == null) {
            log.error("用戶{}不存在", loginUserId);
            return ResultJson.buildError("用戶不存在");
        }

        ResultJson resultJson = workQuery.checkBasicParams();
        if (!ToolsUtil.verifyParams(resultJson)) {
            log.error(resultJson.getMessage());
            return resultJson;
        }
        Integer[] styleLabelForAdd = workQuery.getStyleLabelForAdd();
        Integer[] categoryLabelForAdd = workQuery.getCategoryLabelForAdd();
        if (styleLabelForAdd.length > SysConfigUtil.selectMaxWorkStyleLabelCount()) {
            log.error("画风标签数量过多");
            return ResultJson.buildError("画风标签数量过多");
        }
        if (styleLabelForAdd.length == 0) {
            log.error("请选择画风标签");
            return ResultJson.buildError("请选择画风标签");
        }
        if (categoryLabelForAdd.length > SysConfigUtil.selectMaxWorkCategoryLabelCount()) {
            log.error(resultJson.getMessage());
            return ResultJson.buildError("品类标签数量过多");
        }
        if (categoryLabelForAdd.length == 0) {
            log.error("请选择品类标签");
            return ResultJson.buildError("请选择品类标签");
        }

        if ("N".equalsIgnoreCase(workQuery.getIsNormal())) {
            //身份认证校验 常用站点和创作历史
            if (StringUtils.isEmpty(workQuery.getWebSite())) {
                log.error("常用站点为空");
                return ResultJson.buildError("常用站点为空");
            }
            if (StringUtils.isEmpty(workQuery.getUHistory())) {
                log.error("创作历史为空");
                return ResultJson.buildError("创作历史为空");
            }
        }
        SysWork work = new SysWork();
        work.setContent(workQuery.getContent());
        work.setIsDelete("N");
        work.setName(workQuery.getName());
        work.setUserId(SysConfigUtil.getLoginUserId());
        work.setCreateBy(SysConfigUtil.getLoginUserId());
        work.setCreateTime(new Date());
        work.setPraiseNum(0);
        work.setCommentNum(0);
        work.setShareNum(0);
        work.setRecommendNum(0);
        //作品类型
        work.setIsNormal(StringUtils.isEmpty(workQuery.getIsNormal()) ? "Y" : workQuery.getIsNormal());
        if ("Y".equalsIgnoreCase(workQuery.getIsNormal())) {
            //只有认证的作品才会有作品状态
            work.setStatus(WorkStatusEnum.WORK_STATUS_WAIT.getCode());
        }
        workMapper.insertUseGeneratedKeys(work);
        int workId = work.getId();
        //添加作品图片关联
        Integer[] ids = workQuery.getImageIds();
        List<SysWorkImage> list = new ArrayList<>();
        for (Integer id : ids) {
            SysWorkImage workImage = new SysWorkImage();
            workImage.setImageId(id);
            workImage.setWorkId(workId);
            list.add(workImage);
        }
        workImageMapper.insertList(list);
        //处理标签
        //添加作品标签关联表
        List<Integer> strings = new ArrayList<>();
        strings.addAll(Arrays.asList(styleLabelForAdd));
        strings.addAll(Arrays.asList(categoryLabelForAdd));
        List<SysWorkDict> workDicts = new ArrayList<>();
        for (Integer str : strings) {
            SysWorkDict workDict = new SysWorkDict();
            workDict.setDictId(str);
            workDict.setWorkId(workId);
            workDicts.add(workDict);
        }
        workDictMapper.insertList(workDicts);
        if ("N".equalsIgnoreCase(workQuery.getIsNormal())) {//身认证的处理
            //更新用户
            user.setStatus(UserStatusEnum.USER_STATUS_APPLY.getCode());
            userMapper.updateByPrimaryKeySelective(user);
            //更新用户创作历史和常用站点
            UserAttr userAttr = userAttrMapper.selectByUserId(user.getId());
            userAttr.setUHistory(workQuery.getUHistory());
            userAttr.setWebsite(workQuery.getWebSite());
            userAttrMapper.updateByPrimaryKeySelective(userAttr);
            return ResultJson.buildSuccess("上传作品成功");
        }
        //更新作品发布时间
        UserAttr userAttr = userAttrMapper.selectByUserId(user.getId());
        userAttr.setLastPublishTime(new Date());
        userAttrMapper.updateByPrimaryKeySelective(userAttr);
        //查询所有关注我的人  每人发送一条消息
        //查询所有关注我的人
        UFocus uFocus = new UFocus();
        uFocus.setAuthorId(loginUserId);
        List<UFocus> select = uFocusMapper.select(uFocus);
        List<Integer> toUserList = new ArrayList<>();
        for (UFocus u : select) {
            toUserList.add(u.getUserId());
        }
        MessageQuery messageQuery = new MessageQuery();
        messageQuery.setIsRead("N");
        WorkBack workBack = workMapper.selectById(workId);
        workBack.setNickName(user.getNickName());
        workBack.setRecommendName(user.getNickName());
        workBack.setUserHeadImage(userAttr.getHeadImage());
        messageQuery.setContent(JSON.toJSONString(workBack));
        messageQuery.setSourceType(MessageSourceTypeEnum.message_type_work.getCode());
        messageQuery.setSourceId(workId);
        messageQuery.setAction(MessageActionEnum.MESSAGE_ACTION_PUBLISH.getCode());
        //推荐的类型暂时归结为提醒类型中
        messageQuery.setType(MessageTypeEnum.MESSAGE_TYPE_REM.getCode());
        try {
            messageService.create(messageQuery, loginUserId, toUserList, false);
        } catch (Exception e) {
            log.error("发布作品发送消息异常");
        }
        return ResultJson.buildSuccess("上传作品成功");
    }

//    private WorkBack entityToWorkBack(SysWork work) {
//        WorkBack workBack = new WorkBack();
//        workBack.setCreateTime(work.getCreateTime());
//        workBack.setPraiseNum(work.getPraiseNum());
//        workBack.setStatus(work.getStatus());
//        workBack.setCommentNum(work.getCommentNum());
//        workBack.setContent(work.getContent());
//        workBack.setIsDelete(work.getIsDelete());
//        workBack.setUserId(work.getUserId());
//        workBack.setIsNormal(work.getIsNormal());
//        workBack.setName(work.getName());
//        return workBack;
//    }

    @Override
    public ResultJson selectAllWorkLabels() {
        return ResultJson.buildSuccess(dictMapper.selectAll());
    }

    /**
     * 查询首页的作品列表
     *
     * @param workQuery
     * @return
     */
    @Override
    public Page<WorkBack> workList(WorkQuery workQuery) {
        PageHelper.startPage(workQuery.getPageNo(), workQuery.getPageSize());
        List<WorkBack> sysWorks = workMapper.selectForList(workQuery);
        for (WorkBack back : sysWorks) {
            //从缓存中取出用户信息
            back.setUser(JSONObject.parseObject(InitConfig.userBasicMap.get(back.getUserId()), UserBack.class));
        }
        log.info("查询作品列表成功，当前集合中元素个数：{}", sysWorks.size());
        return (Page<WorkBack>) sysWorks;
    }

    @Override
    public ResultJson workDetail(Integer workId) {
        if (workId <= 0) {
            return ResultJson.buildError("传入id为空");
        }
        WorkBack sysWorkBack = workMapper.selectById(workId);
        if (sysWorkBack == null) {
            return ResultJson.buildError("查询作品详情失败，查询结果为空");
        }
        int userId = SysConfigUtil.getLoginUserId();
        //查询是否是被当前用户喜欢
        int count = wFavoriteMapper.selectCountByWorkId(userId, workId);
        sysWorkBack.setIsLike(count > 0);
        return ResultJson.buildSuccess(sysWorkBack);
    }


    @Override
    public ResultJson selectCommentList(Integer workId) {
        if (workId <= 0) {
            return ResultJson.buildError("传入id为空");
        }
        //获取评论列表
        SysComment comment = new SysComment();
        comment.setTargetId(workId);
        List<CommentBack> select = commentMapper.selectCommentList(comment);
        return ResultJson.buildSuccess(select);

    }


    @Override
    public ResultJson other(WorkQuery query) {
        //查询该作者的其余作品
        Integer authorId = query.getUserId();
        Integer workId = query.getId();
        if (authorId <= 0) {
            log.error("作者id为空");
            return ResultJson.buildError("作者id为空");
        }
        if (workId <= 0) {
            log.error("作品id为空");
            return ResultJson.buildError("作品id为空");
        }
        List<WorkBack> others = workMapper.selectOther(authorId, workId);
        return ResultJson.buildSuccess(others);
    }

//    @Override
//    @Transactional
//    public ResultJson createHistory(String content) {
//        //获取当前登录的用户
//        int loginUserId = SysConfigUtil.getLoginUserId();
//        User user = userMapper.selectByPrimaryKey(loginUserId);
//        if (user == null) {
//            log.error("用戶{}不存在", loginUserId);
//            return ResultJson.buildError("用戶不存在");
//        }
//        UserAttr userAttr = userAttrMapper.selectByUserId(loginUserId);
//        if (userAttr == null) {
//            UserAttr attr = new UserAttr();
//            attr.setUserId(loginUserId);
//            attr.setCreateTime(new Date());
//            attr.setUHistory(content);
//            userAttrMapper.insertSelective(attr);
//        } else {
//            userAttr.setUHistory(content);
//            userAttrMapper.updateByPrimaryKey(userAttr);
//        }
//        user.setStatus(UserStatusEnum.USER_STATUS_STEP2.getCode());
//        userMapper.updateByPrimaryKey(user);
//        return ResultJson.buildSuccess("创作过往填写成功");
//    }
//
//    @Override
//    public ResultJson addWebsite(String website) {
//        //获取当前登录的用户
//        int loginUserId = SysConfigUtil.getLoginUserId();
//        User user = userMapper.selectByPrimaryKey(loginUserId);
//        if (user == null) {
//            log.error("用戶{}不存在", loginUserId);
//            return ResultJson.buildError("用戶不存在");
//        }
//        UserAttr userAttr = userAttrMapper.selectByUserId(loginUserId);
//        if (userAttr == null) {
//            UserAttr attr = new UserAttr();
//            attr.setUserId(loginUserId);
//            attr.setCreateTime(new Date());
//            attr.setWebsite(website);
//            userAttrMapper.insertSelective(attr);
//        } else {
//            userAttr.setWebsite(website);
//            userAttrMapper.updateByPrimaryKey(userAttr);
//        }
//        user.setStatus(UserStatusEnum.USER_STATUS_STEP3.getCode());
//        userMapper.updateByPrimaryKey(user);
//        return ResultJson.buildSuccess("常用网站填写成功");
//    }


    @Override
    @Transactional
    public ResultJson focus(Integer authorId) {
        if (StringUtils.isEmpty(authorId)) {
            return ResultJson.buildError("传入的参数为空");
        }
        User user = userMapper.selectByPrimaryKey(authorId);
        if (user == null) {
            return ResultJson.buildError("用户不存在");
        }
        //校验被关注的人是否是画师
        if (!"Y".equalsIgnoreCase(user.getPainter())) {
            return ResultJson.buildError("用户状态不合法");
        }
        int userId = SysConfigUtil.getLoginUserId();
        int count = uFocusMapper.selectCountById(userId, authorId);
        UFocus uFocus = new UFocus();
        uFocus.setAuthorId(authorId);
        uFocus.setUserId(userId);
        List<Integer> toUserIdList = new ArrayList<>();
        toUserIdList.add(authorId);
        if (count <= 0) {
            uFocus.setCreateTime(new Date());
            uFocusMapper.insertSelective(uFocus);
            //更新用户关注数量
            User updateUser = new User();
            updateUser.setId(authorId);
            updateUser.setFocusCount(user.getFocusCount() + 1);
            try {
                MessageQuery messageQuery = addFocusMessage(uFocus.getUserId());
                messageService.create(messageQuery, userId, toUserIdList, false);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("发送消息异常:{}", e.getMessage());
            }
        } else {
            uFocusMapper.delete(uFocus);
        }
        return ResultJson.buildSuccess(count <= 0 ? "关注成功" : "取消关注成功");
    }


    @Override
    public ResultJson queryFocus(Integer authorId) {
        if (authorId <= 0) {
            return ResultJson.buildError("传入的参数为空");
        }
        User user = userMapper.selectByPrimaryKey(authorId);
        if (user == null) {
            return ResultJson.buildError("用户不存在");
        }
        //校验被关注的人是否是画师
        if (!"Y".equalsIgnoreCase(user.getPainter())) {
            return ResultJson.buildError("用户状态不合法");
        }
        int userId = SysConfigUtil.getLoginUserId();
        int count = uFocusMapper.selectCountById(userId, authorId);
        return ResultJson.buildSuccess(count > 0);
    }

    @Override
    //向所有关注我的人对于此作品进行推荐
    public ResultJson recommend(Integer workId) {
        if (workId <= 0) {
            log.error("作品id为空");
            return ResultJson.buildError("传入参数为空");
        }
        //查询所有关注我的人
        UFocus uFocus = new UFocus();
        int loginUserId = SysConfigUtil.getLoginUserId();
        uFocus.setAuthorId(loginUserId);
        List<UFocus> select = uFocusMapper.select(uFocus);
        //产生一条转发消息  异步进行发送
        // 发送给所有关注我的人
        MessageQuery messageQuery = new MessageQuery();
        messageQuery.setIsRead("N");
        WorkBack workBack = workMapper.selectById(workId);
        //查询作品作者用户信息
        Integer userId = workBack.getUserId();
        UserQuery userQuery = new UserQuery();
        userQuery.setUserID(userId);
        UserBack user = userMapper.selectByUserName(userQuery);
        workBack.setNickName(user.getNickName());
        workBack.setUserHeadImage(user.getUserAttr().getHeadImage());
        //设置推荐人信息
        String recommendName = userMapper.selectByPrimaryKey(loginUserId).getNickName();
        workBack.setRecommendName(recommendName);

        messageQuery.setContent(JSON.toJSONString(workBack));
        messageQuery.setSourceType(MessageSourceTypeEnum.message_type_work.getCode());
        messageQuery.setSourceId(workId);
        messageQuery.setAction(MessageActionEnum.MESSAGE_ACTION_RECOMMEND.getCode());
        //推荐的类型暂时归结为提醒类型中
        messageQuery.setType(MessageTypeEnum.MESSAGE_TYPE_REM.getCode());
        List<Integer> toUserList = new ArrayList<>();
        for (UFocus u : select) {
            toUserList.add(u.getUserId());
        }
        //增加给自己的发送消息
        toUserList.add(loginUserId);
        //发送消息
        try {
            messageService.create(messageQuery, loginUserId, toUserList, false);
        } catch (Exception e) {
            log.error("推荐作品发送消息异常");
        }

        return ResultJson.buildSuccess("推荐作品成功");
    }

    /**
     * 构建关注消息对象
     *
     * @param toUserId 目标用户id
     * @return
     */
    private MessageQuery addFocusMessage(Integer toUserId) {
        MessageQuery messageQuery = new MessageQuery();
        messageQuery.setType(MessageTypeEnum.MESSAGE_TYPE_REM.getCode());
        messageQuery.setIsRead("N");
        messageQuery.setAction(MessageActionEnum.MESSAGE_ACTION_FOCUS.getCode());
        Map<String, String> params = new HashMap<>();
        params.put("from", SysConfigUtil.getLoginUserId() + "");
        params.put("action", MessageActionEnum.MESSAGE_ACTION_FOCUS.getMessage());
        params.put("source", "");
        String content = ToolsUtil.replaceTemplate(SimpleMessageTemplate.content_simple, params);
        messageQuery.setContent(content);
        messageQuery.setCreateTime(new Date());
        messageQuery.setSourceId(toUserId);
        messageQuery.setSourceType(MessageSourceTypeEnum.message_type_user.getCode());
//        messageQuery.setToUserName();
        return messageQuery;
    }


    @Override
    @Transactional
    public ResultJson like(Integer workId, Integer userId) {
        if (workId <= 0) {
            log.error("作品id为空");
            return ResultJson.buildError("作品id为空");
        }
        if (userId <= 0) {
            log.error("用户id为空");
            return ResultJson.buildError("用户id为空");
        }
        SysWork work = workMapper.selectByPrimaryKey(workId);
        if (work == null) {
            return ResultJson.buildError("作品不存在");
        }

        WFavorite wFavorite = new WFavorite();
        wFavorite.setUserId(userId);
        wFavorite.setTargetId(workId);
        wFavorite.setType("work");
        //查询，如果有 表示取消点赞  删除记录
        int count = wFavoriteMapper.selectCountByWorkId(userId, workId);
        if (count <= 0) {
            //新增
            wFavorite.setCreateTime(new Date());
            //更新作品的点赞数量
            work.setPraiseNum(work.getPraiseNum() + 1);
            wFavoriteMapper.insertSelective(wFavorite);
            workMapper.updateByPrimaryKeySelective(work);
            try {
                //查询作品所属用户
                List<Integer> list = new ArrayList<>();
                list.add(work.getUserId());
                MessageQuery messageQuery = makeCommentMessage(work, MessageActionEnum.MESSAGE_ACTION_PRAISE.getCode(), MessageTypeEnum.MESSAGE_TYPE_REM.getCode());
                messageService.create(messageQuery, userId, list, false);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("点赞作品发送消息异常:{}", e.getMessage());
            }
        } else {
            wFavoriteMapper.delete(wFavorite);
            //更新作品的点赞数量
            work.setPraiseNum(work.getPraiseNum() - 1);
            workMapper.updateByPrimaryKeySelective(work);
        }
        return ResultJson.buildSuccess(count <= 0 ? "点赞成功" : "取消点赞成功");
    }

    @Override
    @Transactional
    public ResultJson comment(WCommentQuery commentQuery) {
        ResultJson resultJson = commentQuery.checkParams();
        if (!ToolsUtil.verifyParams(resultJson)) {
            return resultJson;
        }
        SysWork work = workMapper.selectByPrimaryKey(commentQuery.getWorkId());
        if (work == null) {
            return ResultJson.buildError("作品不存在");
        }
        int loginUserId = SysConfigUtil.getLoginUserId();
        User user = userMapper.selectByPrimaryKey(loginUserId);
        SysComment comment = new SysComment();
        comment.setTargetId(commentQuery.getWorkId());
        comment.setTargetType(commentQuery.getTargetType());
        comment.setContent(commentQuery.getContent());
        comment.setCreateTime(new Date());
        comment.setPraiseNum(0);
        comment.setUserId(loginUserId);
        comment.setTopStatus(0);
        comment.setCommentLevel(commentQuery.getCommentLevel());
        comment.setParentId(commentQuery.getParentId());
        commentMapper.insertSelective(comment);

        SysWork work1 = workMapper.selectByPrimaryKey(commentQuery.getWorkId());
        work1.setCommentNum(work1.getCommentNum() + 1);
        workMapper.updateByPrimaryKeySelective(work1);
        int type = MessageTypeEnum.MESSAGE_TYPE_REM.getCode();
        String action = MessageActionEnum.MESSAGE_ACTION_COMMENT.getCode();
        try {
            List<Integer> list = new ArrayList<>();
            list.add(work.getUserId());
            MessageQuery messageQuery = makeCommentMessage(work, action, type);
            messageService.create(messageQuery, loginUserId, list, false);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultJson.buildSuccess("评论成功");
    }

    private MessageQuery makeCommentMessage(SysWork work, String action, Integer type) {
        MessageQuery message = new MessageQuery();
        //提醒
        message.setType(MessageTypeEnum.MESSAGE_TYPE_REM.getCode());
        message.setAction(action);
        //组装消息内容
        Map<String, String> params = new HashMap<>();
        params.put("from", SysConfigUtil.getLoginUserId() + "");
        params.put("action", MessageActionEnum.MESSAGE_ACTION_PRAISE.getMessage());
        params.put("source", "的作品《" + work.getName() + "》");
        String content = ToolsUtil.replaceTemplate(SimpleMessageTemplate.content_simple, params);
        message.setCreateTime(new Date());
        message.setContent(content);
        message.setSourceId(work.getId());
        message.setSourceType(MessageSourceTypeEnum.message_type_work.getCode());
        Integer toUserId = work.getUserId();
        message.setIsRead("N");
        message.setType(type);
        return message;
    }

    @Override
    @Transactional
    public ResultJson commentLike(int id) {
        if (id < 0) {
            return ResultJson.buildError("传入参数为空");
        }
        SysComment comment = commentMapper.selectByPrimaryKey(id);
        if (comment == null) {
            return ResultJson.buildError("对象不存在");
        }
        comment.setPraiseNum(comment.getPraiseNum() + 1);
        commentMapper.updateByPrimaryKeySelective(comment);
        return ResultJson.buildSuccess("点赞成功");
    }


    @Override
    @Transactional
    public ResultJson commentDel(int id) {
        if (id < 0) {
            return ResultJson.buildError("传入参数为空");
        }
        SysComment comment = commentMapper.selectByPrimaryKey(id);
        if (comment == null) {
            return ResultJson.buildError("对象不存在");
        }
        if (comment.getCommentLevel() > 1 && comment.getParentId() > 0) {
            //为子评论 删除自己既可
            commentMapper.deleteByPrimaryKey(id);
        }
        if (comment.getCommentLevel() == 1 && comment.getParentId() == 0) {
            //为父评论 删除自己以及全部的子评论  目前评论支持二级，
            List<Integer> list = commentMapper.selectByParentId(id);
            list.add(id);
            commentMapper.deleteByBatch(list);
        }
        return ResultJson.buildSuccess("删除评论成功");
    }

    @Override
    @Transactional
    public ResultJson workCheck(WorkQuery workQuery) {
        if (workQuery.getId() <= 0) {
            log.error("传入参数id错误:{}", workQuery.getId());
            return ResultJson.buildError("传入参数错误");
        }
        if (workQuery.getStatus() <= 0 ) {
            log.error("传入参数status错误:{}", workQuery.getStatus());
            return ResultJson.buildError("传入参数错误");
        }
        SysWork work = workMapper.selectByPrimaryKey(workQuery.getId());
        if (work == null || work.getId() <= 0) {
            return ResultJson.buildError("对象不存在");
        }
        work.setStatus(workQuery.getStatus());
        if (WorkStatusEnum.WORK_STATUS_FAIL.getCode() == workQuery.getStatus()) {
            work.setFailReason(workQuery.getFailReason());
        }
        workMapper.updateByPrimaryKeySelective(work);
        return ResultJson.buildSuccess();
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
                    if (file.getSize() > 1024 * 1024 * 2) {
                        result = "图片大小不符合规范，请重新上传";
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public ResultJson reportList() {
        List<SysReport> sysReports = reportMapper.selectAll();
        return ResultJson.buildSuccess(sysReports);
    }

    @Override
    public ResultJson report(String ids, Integer workId) {
        if (StringUtils.isEmpty(ids)) {
            return ResultJson.buildError("传入参数为空");
        }
        String[] split = ids.split("-");
        List<SysReportRecord> list = new ArrayList<>();
        for (String str : split) {
            int report_id = Integer.parseInt(str);
            SysReportRecord reportRecord = new SysReportRecord();
            reportRecord.setUserId(SysConfigUtil.getLoginUserId());
            reportRecord.setReportId(report_id);
            reportRecord.setCreateTime(new Date());
            reportRecord.setWorkId(workId);
            list.add(reportRecord);
        }
        reportRecordMapper.insertList(list);
        return ResultJson.buildSuccess();
    }


    @Override
    public ResultJson getUserInfoByWorkId(Integer workId) {
        if (workId == null || workId <= 0) {
            log.error("传入参数为空");
            return ResultJson.buildError("传入参数为空");
        }
        SysWork work = workMapper.selectByPrimaryKey(workId);
        if (work == null || work.getId() <= 0) {
            log.error("对象不存在");
            return ResultJson.buildError("对象不存在");
        }
        UserBack userBack = workMapper.selectUserInfoByWorkId(workId);
        return ResultJson.buildSuccess(userBack);
    }


    @Override
    @Transactional
    public ResultJson workDel(Integer workId) {
        if (workId == null || workId <= 0) {
            log.error("传入参数为空");
            return ResultJson.buildError("传入参数为空");
        }
        SysWork work = workMapper.selectByPrimaryKey(workId);
        if (work == null || work.getId() <= 0) {
            log.error("对象不存在");
            return ResultJson.buildError("对象不存在");
        }
        work.setIsDelete("Y");
        work.setUpdateTime(new Date());
        workMapper.updateByPrimaryKeySelective(work);
        //删除作品标签关联
        workDictMapper.deleteTagsByWork(workId);
        //删除作品评论
        commentMapper.deleteByWorkId(workId);
        //删除点赞作品
        wFavoriteMapper.deleteByWorkId(null, workId);
        //删除作品关联
        SysWorkImage workImage = new SysWorkImage();
        workImage.setWorkId(workId);
        //查询所有图片
        List<SysImage> sysImages = workImageMapper.selectImagesByWorkId(workId);
        Integer[] ids = null;
        if (!CollectionUtils.isEmpty(sysImages)) {
            ids = new Integer[sysImages.size()];
            for (int i = 0; i < sysImages.size(); i++) {
                ids[i] = sysImages.get(i).getId();
            }
        }
        workImageMapper.delete(workImage);
        //删除图片表
        imageMapper.deletByIds(ids);
        //删除服务器上图片
        return ResultJson.buildSuccess();
    }
}