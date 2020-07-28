package com.example.lixc.service.impl;

import com.alibaba.fastjson.JSON;
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
import com.example.lixc.vo.back.WorkBack;
import com.example.lixc.vo.query.MessageQuery;
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
    private WCommentMapper wCommentMapper;

    @Autowired
    private WCommentMapper commentMapper;
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
        work.setStatus(WorkStatusEnum.WORK_STATUS_WAIT.getCode());
        work.setUserId(SysConfigUtil.getLoginUserId());
        work.setCreateBy(SysConfigUtil.getLoginUserId());
//        work.setUserId(0);
//        work.setCreateBy(0);
        work.setCreateTime(new Date());
        work.setPraiseNum(0);
        work.setIsNormal(StringUtils.isEmpty(workQuery.getIsNormal()) ? "Y" : workQuery.getIsNormal());
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
    public ResultJson getWorkComment(Integer workId) {
        if (workId <= 0) {
            return ResultJson.buildError("传入id为空");
        }
        //获取评论列表
        WComment comment = new WComment();
        comment.setWorkId(workId);
        List<WComment> select = wCommentMapper.select(comment);
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

    @Override
    @Transactional
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


    @Override
    @Transactional
    public ResultJson focus(String toUserId) {
        if (StringUtils.isEmpty(toUserId)) {
            return ResultJson.buildError("传入的参数为空");
        }
        User user = userMapper.selectByPrimaryKey(toUserId);
        if (user == null) {
            return ResultJson.buildError("用户不存在");
        }
        //校验被关注的人是否是画师
        if (!"Y".equalsIgnoreCase(user.getPainter())) {
            return ResultJson.buildError("用户状态不合法");
        }
        UFocus param = new UFocus();
        param.setAuthorId(Integer.valueOf(toUserId));
        param.setUserId(SysConfigUtil.getLoginUserId());
        List<UFocus> uFocusList = uFocusMapper.select(param);
        UFocus uFocus = new UFocus();
        if (CollectionUtils.isEmpty(uFocusList)) {
            uFocus.setAuthorId(Integer.valueOf(toUserId));
            uFocus.setUserId(SysConfigUtil.getLoginUserId());
            uFocus.setCreateTime(new Date());
            uFocus.setCancel("N");
            uFocusMapper.insertSelective(uFocus);
        } else {
            uFocus = uFocusList.get(0);
            if ("Y".equalsIgnoreCase(uFocus.getCancel())) {
                uFocus.setCancel("N");
                uFocusMapper.updateByPrimaryKeySelective(uFocus);
            }
        }
        try {
            MessageQuery messageQuery = addFocusMessage(uFocus.getUserId(), uFocus.getAuthorId());
            List<MessageQuery> messageQueryList = new ArrayList<>();
            messageQueryList.add(messageQuery);
            messageService.create(messageQueryList, false);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送消息异常:{}", e.getMessage());
        }
        return ResultJson.buildSuccess("关注成功");
    }

    @Override
    public ResultJson cancelFocus(String toUserId) {
        if (StringUtils.isEmpty(toUserId)) {
            return ResultJson.buildError("传入的参数为空");
        }
        User user = userMapper.selectByPrimaryKey(toUserId);
        if (user == null) {
            return ResultJson.buildError("用户不存在");
        }
        UFocus param = new UFocus();
        param.setAuthorId(Integer.valueOf(toUserId));
        param.setUserId(SysConfigUtil.getLoginUserId());
        List<UFocus> uFocusList = uFocusMapper.select(param);
        if (CollectionUtils.isEmpty(uFocusList)) {
            return ResultJson.buildError("记录不存在");
        }
        UFocus uFocus = uFocusList.get(0);
        uFocus.setCancel("Y");
        uFocusMapper.updateByPrimaryKeySelective(uFocus);
        return ResultJson.buildSuccess("取消关注成功");
    }

    @Override
    //向所有关注我的人对于此作品进行推荐
    public ResultJson recommend(String workId) {
        if (StringUtils.isEmpty(workId) || Integer.parseInt(workId) <= 0) {
            return ResultJson.buildError("传入参数为空");
        }
        //查询所有关注我的人
        UFocus uFocus = new UFocus();
        int loginUserId = SysConfigUtil.getLoginUserId();
        uFocus.setAuthorId(loginUserId);
        //未取消关注
        uFocus.setCancel("N");
        List<UFocus> select = uFocusMapper.select(uFocus);
        //产生一条转发消息  异步进行发送  //发送给所有关注我的人
        MessageQuery messageQuery = new MessageQuery();
        messageQuery.setIsRead("N");
        messageQuery.setFromUserId(loginUserId);
        WorkBack workBack = workMapper.selectById(Integer.parseInt(workId));
        messageQuery.setContent(JSON.toJSONString(workBack));
        messageQuery.setSourceType(MessageSourceTypeEnum.message_type_work.getCode());
        messageQuery.setSourceId(Integer.parseInt(workId));
        messageQuery.setAction(MessageActionEnum.MESSAGE_ACTION_RECOMMEND.getCode());
        //推荐的类型暂时归结为提醒类型中
        messageQuery.setType(MessageTypeEnum.MESSAGE_TYPE_REM.getCode());
        List<MessageQuery> messageQueryList = new ArrayList<>();
        for (UFocus u : select) {
            messageQuery.setToUserId(u.getUserId());
            messageQueryList.add(messageQuery);
        }
        messageService.create(messageQueryList, false);
        return null;
    }

    /**
     * 构建关注消息对象
     *
     * @param toUserId    目标用户id
     * @param fromIUserId 来源用户id
     * @return
     */
    private MessageQuery addFocusMessage(Integer toUserId, Integer fromIUserId) {
        MessageQuery messageQuery = new MessageQuery();
        messageQuery.setType(MessageTypeEnum.MESSAGE_TYPE_REM.getCode());
        messageQuery.setToUserId(toUserId);
        messageQuery.setFromUserId(fromIUserId);
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
    public ResultJson like(String workId, Integer fromUserId) {
        if (StringUtils.isEmpty(workId)) {
            log.error("作品id为空");
            return ResultJson.buildError("作品id为空");
        }
        if (fromUserId <= 0) {
            log.error("用户id为空");
            return ResultJson.buildError("用户id为空");
        }
        SysWork work = workMapper.selectByPrimaryKey(workId);
        if (work == null) {
            return ResultJson.buildError("作品不存在");
        }
        WFavorite wFavorite = new WFavorite();
        wFavorite.setCreateTime(new Date());
        wFavorite.setUserId(fromUserId);
        wFavorite.setTargetID(Integer.parseInt(workId));
        wFavorite.setType("work");
        wFavoriteMapper.insertSelective(wFavorite);
        //更新作品的点赞数量
        work.setPraiseNum(work.getPraiseNum() + 1);
        workMapper.updateByPrimaryKeySelective(work);
        try {
            MessageQuery messageQuery = makeCommentMessage(work, MessageActionEnum.MESSAGE_ACTION_PRAISE.getCode(), MessageTypeEnum.MESSAGE_TYPE_REM.getCode());
            List<MessageQuery> list = new ArrayList<MessageQuery>();
            list.add(messageQuery);
            messageService.create(list, false);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("点赞作品发送消息异常:{}", e.getMessage());
        }
        return ResultJson.buildSuccess("点赞成功");
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
        WComment comment = new WComment();
        comment.setWorkId(commentQuery.getWorkId());
        comment.setContent(commentQuery.getContent());
        comment.setCreateTime(new Date());
        comment.setPraiseNum(0);
        comment.setUserId(loginUserId);
        comment.setUserName(user.getNickName());
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
            MessageQuery messageQuery = makeCommentMessage(work, action, type);
            List<MessageQuery> messageQueryList = new ArrayList<>();
            messageQueryList.add(messageQuery);
            messageService.create(messageQueryList, false);
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
        message.setToUserId(toUserId);
        message.setFromUserId(SysConfigUtil.getLoginUserId());
        message.setIsRead("N");
        message.setType(type);
        return message;
    }

    @Override
    public ResultJson commentLike(int id) {
        if (id < 0) {
            return ResultJson.buildError("传入参数为空");
        }
        WComment comment = commentMapper.selectByPrimaryKey(id);
        if (comment == null) {
            return ResultJson.buildError("对象不存在");
        }
        comment.setPraiseNum(comment.getPraiseNum() + 1);
        commentMapper.updateByPrimaryKeySelective(comment);
        return ResultJson.buildSuccess("点赞成功");
    }


    @Override
    public ResultJson commentDel(int id) {
        if (id < 0) {
            return ResultJson.buildError("传入参数为空");
        }
        WComment comment = commentMapper.selectByPrimaryKey(id);
        if (comment == null) {
            return ResultJson.buildError("对象不存在");
        }
        commentMapper.deleteByPrimaryKey(id);
        return ResultJson.buildSuccess("删除成功");
    }

    @Override
    @Transactional
    public ResultJson workCheck(WorkQuery workQuery) {
        if (workQuery.getId() <= 0) {
            log.error("传入参数id错误:{}", workQuery.getId());
            return ResultJson.buildError("传入参数错误");
        }
        if (workQuery.getStatus() <= 0 || workQuery.getStatus() != WorkStatusEnum.WORK_STATUS_WAIT.getCode()) {
            log.error("传入参数status错误:{}", workQuery.getStatus());
            return ResultJson.buildError("传入参数错误");
        }
        SysWork work = workMapper.selectByPrimaryKey(workQuery.getId());
        if (work == null || work.getId() <= 0) {
            return ResultJson.buildError("对象不存在");
        }
        work.setStatus(workQuery.getStatus());
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
    public ResultJson report(String ids) {
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
            list.add(reportRecord);
        }
        reportRecordMapper.insertList(list);
        return ResultJson.buildSuccess();
    }


}