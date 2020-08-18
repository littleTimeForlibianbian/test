package com.example.lixc.service.impl;

import com.example.lixc.config.InitConfig;
import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.entity.*;
import com.example.lixc.enums.MessageTypeEnum;
import com.example.lixc.mapper.*;
import com.example.lixc.service.MyWorldService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.back.VersionSpoBack;
import com.example.lixc.vo.back.WorkBack;
import com.example.lixc.vo.query.MessageQuery;
import com.example.lixc.vo.query.UserMessageQuery;
import com.example.lixc.vo.query.UserQuery;
import com.example.lixc.vo.query.VersionSpoQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/1 17:39
 */
@Slf4j
@Service
public class MyWorldServiceImpl implements MyWorldService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAttrMapper userAttrMapper;

    @Autowired
    private SysWorkMapper workMapper;

    @Autowired
    private SysUserMessageMapper userMessageMapper;

    @Autowired
    private SysMessageMapper messageMapper;

    @Autowired
    private WFavoriteMapper favoriteMapper;

    @Autowired
    private UFocusMapper focusMapper;

    @Autowired
    private SysSuggestMapper suggestMapper;

    @Autowired
    private WFavoriteMapper wFavoriteMapper;

    @Autowired
    private SysVersionSpoMapper versionSpoMapper;

    @Override
    public ResultJson newMessage() {
        //查询我的消息中全部未读的消息 从这里能查到的用户 肯定都是存在的用户
        int userId = SysConfigUtil.getLoginUserId();
        //查到了用户和消息的关联关系
        List<SysUserMessage> sysUserMessages = userMessageMapper.selectList(userId, "N");
        List<SysMessage> messageList = new ArrayList<>();
        for (SysUserMessage userMessage : sysUserMessages) {
            //最新消息一般是后台管理员所发，需要带有用户头像，所有管理员都是用默认头像
            Integer messageId = userMessage.getMessageId();
            SysMessage message = messageMapper.selectByPrimaryKey(messageId);
            messageList.add(message);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("messageList", messageList);
        resultMap.put("headImageUrl", SysConfigUtil.selectDefaultImageUrl());
        return ResultJson.buildSuccess(resultMap);
    }

    @Override
    public ResultJson newMessageRead(int messageId) {
        if (messageId < 0) {
            return ResultJson.buildError("参数传入不合法");
        }
        SysMessage message = messageMapper.selectByPrimaryKey(messageId);
        if (message == null) {
            return ResultJson.buildError("对象不存在");
        }
        int userId = SysConfigUtil.getLoginUserId();
        SysUserMessage userMessage = new SysUserMessage();
        userMessage.setMessageId(messageId);
        userMessage.setToUserId(userId);
        SysUserMessage result = userMessageMapper.selectOne(userMessage);
        //设置已读
        result.setIsRead("Y");
        userMessageMapper.updateByPrimaryKeySelective(result);
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson myLike() {
        WFavorite favorite = new WFavorite();
        favorite.setUserId(SysConfigUtil.getLoginUserId());
        favorite.setType("work");
        //查询到我喜欢的全部的作品id
        List<WFavorite> list = favoriteMapper.select(favorite);
        List<WorkBack> result = new ArrayList<>();
        for (WFavorite w : list) {
            WorkBack workBack = workMapper.selectById(w.getTargetId());
            result.add(workBack);
        }
        return ResultJson.buildSuccess(result);
    }

    @Override
    public ResultJson myFocus() {
        int currentUserID = SysConfigUtil.getLoginUserId();
        UFocus focus = new UFocus();
        focus.setUserId(currentUserID);
        List<UFocus> select = focusMapper.select(focus);
        List<UserBack> result = new ArrayList<>();
        for (UFocus u : select) {
            Integer userId = u.getAuthorId();
            UserQuery userQuery = new UserQuery();
            userQuery.setUserID(userId);
            UserBack user = userMapper.selectByUserName(userQuery);
            result.add(user);
        }
        return ResultJson.buildSuccess(result);
    }

    @Override
    public ResultJson news() {
        int userId = SysConfigUtil.getLoginUserId();
        //查到了用户和消息的关联关系
        List<SysUserMessage> sysUserMessages = userMessageMapper.selectList(userId, "N");
        List<SysMessage> messageList = new ArrayList<>();
        for (SysUserMessage userMessage : sysUserMessages) {
            Integer messageId = userMessage.getMessageId();
            SysMessage message = messageMapper.selectByPrimaryKey(messageId);
            if (MessageTypeEnum.MESSAGE_TYPE_ANN.getCode() != message.getType()) {
                messageList.add(message);
            }
        }
        return ResultJson.buildSuccess(messageList);
    }

    @Override
    public ResultJson systemMessage() {
        int userId = SysConfigUtil.getLoginUserId();
        //查到了用户和消息的关联关系
        List<SysUserMessage> sysUserMessages = userMessageMapper.selectList(userId, "N");
        List<SysMessage> messageList = new ArrayList<>();
        for (SysUserMessage userMessage : sysUserMessages) {
            //最新消息一般是后台管理员所发，需要带有用户头像，所有管理员都是用默认头像
            Integer messageId = userMessage.getMessageId();
            SysMessage message = messageMapper.selectByPrimaryKey(messageId);
            if (MessageTypeEnum.MESSAGE_TYPE_ANN.getCode() == message.getType()) {
                messageList.add(message);
            }
        }
        return ResultJson.buildSuccess(messageList);
    }

    @Override
    public ResultJson addFeedBack(String content) {
        if (StringUtils.isEmpty(content)) {
            return ResultJson.buildError("参数为空");
        }
        SysSuggest suggest = new SysSuggest();
        suggest.setContent(content);
        suggest.setCreateTime(new Date());
        int loginUserId = SysConfigUtil.getLoginUserId();
        suggest.setUserId(loginUserId);
        suggest.setUserName(InitConfig.getNickName(loginUserId));
        UserAttr userAttr = userAttrMapper.selectByUserId(loginUserId);
        suggest.setPraiseNum(0);
        //设置优先级为最低
        suggest.setPriority(0);
        //1表示待优化 2表示优化中  3 表示 优化完毕
        suggest.setStatus(1);
        suggest.setVersion(SysConfigUtil.selectCurrentVersion());
        suggest.setUrl(userAttr.getHeadImage());
        suggestMapper.insertSelective(suggest);
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson feedBackOptList(VersionSpoQuery versionSpoQuery) {
        List<VersionSpoBack> versionSpoBacks = versionSpoMapper.selectForList(versionSpoQuery);
        if (!CollectionUtils.isEmpty(versionSpoBacks)) {
            for (VersionSpoBack back : versionSpoBacks) {
                if (back.getStatus() == 0) {
                    back.setStatusCh("待优化");
                }
                if (back.getStatus() == 1) {
                    back.setStatusCh("优化中");
                }
                if (back.getStatus() == 2) {
                    back.setStatusCh("优化完毕");
                }
            }
        }
        return ResultJson.buildSuccess(versionSpoBacks);
    }

    @Override
    public ResultJson versionSpoiler(VersionSpoQuery versionSpoQuery) {
        List<VersionSpoBack> versionSpoBacks = versionSpoMapper.selectForList(versionSpoQuery);
        return ResultJson.buildSuccess(versionSpoBacks);
    }

    @Override
    public ResultJson allMessage() {
        return null;
    }

    @Override
    public ResultJson queryCount(Integer userId) {
        int workId = -1;
        if (userId == null || userId <= 0) {
            log.error("用户id为空");
            return ResultJson.buildError("用户id为空");
        }
        //喜欢作品
        int myLikeCount = favoriteMapper.selectCountByWorkId(userId, workId);

        //关注的人
        UFocus focus = new UFocus();
        focus.setUserId(userId);
        List<UFocus> select = focusMapper.select(focus);
        Map<String, Integer> result = new HashMap<>();
        //动态消息
        UserMessageQuery messageQuery = new UserMessageQuery();
        messageQuery.setToUserId(userId);
        messageQuery.setType(MessageTypeEnum.MESSAGE_TYPE_REM.getCode());

        int newsCount = userMessageMapper.queryCount(messageQuery);
        //系统消息
        messageQuery.setType(MessageTypeEnum.MESSAGE_TYPE_ANN.getCode());
        int sysCount = userMessageMapper.queryCount(messageQuery);


        result.put("myLikeCount", myLikeCount);
        result.put("newsCount", newsCount);
        result.put("sysCount", sysCount);
        result.put("myFocus", select.size());

        return ResultJson.buildSuccess(result);
    }

    @Override
    public ResultJson feedBackLike(Integer id) {
        SysSuggest suggest = suggestMapper.selectByPrimaryKey(id);
        if (suggest == null || id <= 0) {
            return ResultJson.buildError("对象不存在");
        }
        int userId = SysConfigUtil.getLoginUserId();
        //根据点赞id和userId去记
        WFavorite wFavorite = new WFavorite();
        wFavorite.setUserId(userId);
        wFavorite.setTargetId(id);
        wFavorite.setType("feedback");

        List<WFavorite> select = wFavoriteMapper.select(wFavorite);
        if (!CollectionUtils.isEmpty(select)) {
            //
            wFavoriteMapper.delete(wFavorite);
            suggest.setPraiseNum(suggest.getPraiseNum() - 1);
            suggestMapper.updateByPrimaryKeySelective(suggest);
            return ResultJson.buildSuccess("取消点赞成功");
        } else {
            wFavorite.setCreateTime(new Date());
            wFavoriteMapper.insertSelective(wFavorite);
            suggest.setPraiseNum(suggest.getPraiseNum() + 1);
            suggestMapper.updateByPrimaryKeySelective(suggest);
            return ResultJson.buildSuccess("点赞成功");
        }
    }

    @Override
    public ResultJson feedBackList() {
        List<SysSuggest> data = suggestMapper.selectAll();
        return ResultJson.buildSuccess(data);
    }

    /**
     * 查询 我的世界主页中所有的推荐消息和关注的人发布作品消息
     *
     * @param userId 接受者id
     * @return
     */
    @Override
    public ResultJson recommendMessage(int userId) {
        if (userId <= 0) {
            log.error("当前登录用户为空");
            return ResultJson.buildError("当前登录用户为空");
        }
        //查询到作品相关信息
        List<SysMessage> messageList = userMessageMapper.queryHomeMessage(userId);
        return ResultJson.buildSuccess(messageList);
    }
}
