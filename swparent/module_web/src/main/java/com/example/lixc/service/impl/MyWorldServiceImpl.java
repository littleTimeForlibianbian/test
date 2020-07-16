package com.example.lixc.service.impl;

import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.entity.*;
import com.example.lixc.enums.MessageTypeEnum;
import com.example.lixc.mapper.*;
import com.example.lixc.service.MyWorldService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.back.WorkBack;
import com.example.lixc.vo.query.MessageQuery;
import com.example.lixc.vo.query.UserQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/1 17:39
 */
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

    @Override
    public ResultJson newMessage() {
        //查询我的消息中全部未读的消息 从这里能查到的用户 肯定都是存在的用户
        int loginUserId = SysConfigUtil.getLoginUserId();
        MessageQuery messageQuery = new MessageQuery();
        messageQuery.setToUserId(loginUserId);
        messageQuery.setIsRead("N");
        //查到了用户和消息的关联关系
        List<SysUserMessage> sysUserMessages = userMessageMapper.selectList(messageQuery);
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
        //没有私心的话 应该不会有重发消息的场景   暂时确定一个人 一个消息只有一条记录
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
        int loginUserId = SysConfigUtil.getLoginUserId();
        MessageQuery messageQuery = new MessageQuery();
        messageQuery.setToUserId(loginUserId);
        //查到了用户和消息的关联关系
        List<SysUserMessage> sysUserMessages = userMessageMapper.selectList(messageQuery);
        List<SysMessage> messageList = new ArrayList<>();
        for (SysUserMessage userMessage : sysUserMessages) {
            //最新消息一般是后台管理员所发，需要带有用户头像，所有管理员都是用默认头像
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
        int loginUserId = SysConfigUtil.getLoginUserId();
        MessageQuery messageQuery = new MessageQuery();
        messageQuery.setToUserId(loginUserId);
        //查到了用户和消息的关联关系
        List<SysUserMessage> sysUserMessages = userMessageMapper.selectList(messageQuery);
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
        UserAttr userAttr = userAttrMapper.selectByUserId(loginUserId);
        suggest.setPraiseNum(0);
        suggest.setVersion(SysConfigUtil.selectCurrentVersion());
        suggest.setUrl(userAttr.getHeadImage());
        suggestMapper.insertSelective(suggest);
        return ResultJson.buildSuccess();
    }

    @Override
    //TODO
    public ResultJson feedBackOpt() {
        return null;
    }

    @Override
    //TODO
    public ResultJson versionSpoiler() {
        return null;
    }

    @Override
    //TODO
    public ResultJson allMessage() {
        return null;
    }

    @Override
    public ResultJson queryCount() {
        int userId = SysConfigUtil.getLoginUserId();
        int workId = -1;
        int myLikeCount = favoriteMapper.selectCountByWorkId(userId, workId);

        UFocus focus = new UFocus();
        focus.setUserId(userId);
        List<UFocus> select = focusMapper.select(focus);
        Map<String, Integer> result = new HashMap<>();

        result.put("myLikeCount", myLikeCount);
        result.put("myFocus", select.size());

        return ResultJson.buildSuccess(result);
    }

    @Override
    public ResultJson feedBackLike(String id) {
        SysSuggest suggest = suggestMapper.selectByPrimaryKey(id);
        if (suggest == null) {
            return ResultJson.buildError("对象不存在");
        }
        int userId = SysConfigUtil.getLoginUserId();
        WFavorite wFavorite = new WFavorite();
        wFavorite.setCreateTime(new Date());
        wFavorite.setUserId(SysConfigUtil.getLoginUserId());
        wFavorite.setTargetId(Integer.parseInt(id));
        wFavorite.setType("feedback");
        wFavoriteMapper.insertSelective(wFavorite);
        suggest.setPraiseNum(suggest.getPraiseNum() + 1);
        suggestMapper.updateByPrimaryKeySelective(suggest);
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson feedBackList() {
        return ResultJson.buildSuccess(suggestMapper.selectAll());
    }
}
