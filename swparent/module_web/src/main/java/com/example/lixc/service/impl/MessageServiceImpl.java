package com.example.lixc.service.impl;

import com.example.lixc.config.InitConfig;
import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.entity.SysMessage;
import com.example.lixc.entity.SysUserMessage;
import com.example.lixc.enums.MessageTypeEnum;
import com.example.lixc.mapper.SysMessageMapper;
import com.example.lixc.mapper.SysUserMessageMapper;
import com.example.lixc.service.MessageService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.util.ToolsUtil;
import com.example.lixc.vo.back.MessageBack;
import com.example.lixc.vo.query.MessageQuery;
import com.example.lixc.vo.query.UserMessageQuery;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lixc
 * @Description 消息服务service
 * @createTime 2020/7/1 11:07
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {


    @Autowired
    private SysMessageMapper messageMapper;

    @Autowired
    private SysUserMessageMapper userMessageMapper;

    /**
     * @param messageQuery messageQuery对象
     * @param fromUserId   发送者id
     * @param toUserIdList 接受者id集合
     * @param isTimed      是否是定时发送，y:是  n:false
     */
    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MessageQuery messageQuery, Integer fromUserId, List<Integer> toUserIdList, boolean isTimed) {
        try {
            log.info("生成消息记录start");
            SysMessage message = changeToMessage(messageQuery);
            messageMapper.insertUseGeneratedKeys(message);
            log.info("生成消息记录end");
            for (Integer toUserId : toUserIdList) {
                if (!isTimed) {
                    log.info("非定时发送");
                    log.info(" send message  start");
                    SysUserMessage userMessage = new SysUserMessage();
                    userMessage.setIsRead("N");
                    userMessage.setToUserId(toUserId);
                    userMessage.setMessageId(message.getId());
                    userMessage.setFromUserId(fromUserId);
                    userMessage.setSendTime(new Date());
                    userMessageMapper.insertSelective(userMessage);
                    log.info(" send message end");
                } else {
                    //TODO  定时发送
                }
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    private SysMessage changeToMessage(MessageQuery messageQuery) {
        SysMessage message = new SysMessage();
        message.setCreateTime(new Date());
        message.setTitle(messageQuery.getTitle());
        message.setSourceType(messageQuery.getSourceType());
        message.setSourceId(messageQuery.getSourceId());
        message.setAction(messageQuery.getAction());
        message.setType(messageQuery.getType());
        message.setContent(messageQuery.getContent());
        return message;
    }

    @Override
    public MessageBack queryMessage(Integer messageId) {
        //查询消息详情
        MessageBack message = messageMapper.selectDetail(messageId);
        message.setNickName(InitConfig.getNickName(message.getCreateBy()));
        return message;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(MessageQuery messageQuery) {
        //插入消息记录表
        log.info(" send message  start");
        SysUserMessage userMessage = new SysUserMessage();
        userMessage.setIsRead("N");
        userMessage.setMessageId(messageQuery.getMessageId());
        userMessage.setSendTime(new Date());
        userMessageMapper.insertSelective(userMessage);
        log.info(" send message end");
    }

    @Override
    public List<SysMessage> queryNotRead(UserMessageQuery userMessageQuery) {
        int loginUserId = SysConfigUtil.getLoginUserId();
        userMessageQuery.setToUserId(loginUserId);
        userMessageQuery.setIsRead("N");
        //查询所有的未读消息
        return userMessageMapper.query(userMessageQuery);
    }

    @Override
    public int queryNotReadCount(UserMessageQuery userMessageQuery) {
        int loginUserId = SysConfigUtil.getLoginUserId();
        userMessageQuery.setToUserId(loginUserId);
        userMessageQuery.setIsRead("N");
        //查询所有的未读消息
        return userMessageMapper.queryCount(userMessageQuery);
    }

    @Override
    public List<SysMessage> querySystem(UserMessageQuery userMessageQuery) {
        //查询所有的系统消息
        userMessageQuery.setType(MessageTypeEnum.MESSAGE_TYPE_ANN.getCode());
        return userMessageMapper.query(userMessageQuery);
    }

    @Override
    public List<SysMessage> queryDynamic(UserMessageQuery userMessageQuery) {
        //查询所有的系统消息
        userMessageQuery.setType(MessageTypeEnum.MESSAGE_TYPE_REM.getCode());
        userMessageQuery.setIsRead("N");
        int loginUserId = SysConfigUtil.getLoginUserId();
        userMessageQuery.setToUserId(loginUserId);
        return userMessageMapper.query(userMessageQuery);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultJson deleteById(Integer messageId) {
        if (messageId <= 0) {
            log.error("消息id为空");
            return ResultJson.buildError("消息id为空");
        }
        //查询消息状态
        SysMessage message = messageMapper.selectByPrimaryKey(messageId);
        //删除消息
        messageMapper.deleteByPrimaryKey(messageId);
        if (message.getSendTime().after(new Date())) {
            //如果消息已经发送
            SysUserMessage userMessage = new SysUserMessage();
            userMessage.setMessageId(messageId);
            userMessageMapper.delete(userMessage);
        }
        return ResultJson.buildSuccess();
    }

    @Override
    public List<MessageBack> querySysMessage(MessageQuery messageQuery) {
        PageHelper.startPage(messageQuery.getPageNo(), messageQuery.getPageSize());
        List<MessageBack> messageList = messageMapper.selectList(messageQuery);
        if (!CollectionUtils.isEmpty(messageList)) {
            for (SysMessage m : messageList) {
                Integer userId = m.getCreateBy();
                if (userId != null && userId > 0) {
                    MessageBack back = (MessageBack) m;
                    back.setNickName(InitConfig.getNickName(userId));
                }

            }
        }
        return messageList;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultJson addMessage(MessageQuery messageQuery) {
        int fromUserId = SysConfigUtil.getLoginUserId();
        ResultJson result = messageQuery.checkParams();
        if (!ToolsUtil.verifyParams(result)) {
            return result;
        }
        SysMessage message = convertToMessage(messageQuery, fromUserId);
        messageMapper.insertSelective(message);
        log.info("添加消息成功");
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson edit(MessageQuery messageQuery) {
        if (messageQuery.getMessageId() == null || messageQuery.getMessageId() <= 0) {
            log.error("编辑系统消息id为空");
            return ResultJson.buildError("id为空");
        }
        ResultJson result = messageQuery.checkParams();
        if (!ToolsUtil.verifyParams(result)) {
            return result;
        }
        SysMessage message = messageMapper.selectByPrimaryKey(messageQuery.getMessageId());
        if (message == null) {
            log.error("消息不存在或者已经被删除，消息id:{}", messageQuery.getMessageId());
            return ResultJson.buildError("消息不存在或者已经被删除");
        }
        if (messageQuery.getSendTime().before(new Date())) {
            //消息已经发送
            log.info("消息已经发送，发送时间：{}", messageQuery.getSendTime());
        }
        message.setSendTime(messageQuery.getSendTime());
        message.setTitle(messageQuery.getTitle());
        message.setContent(messageQuery.getContent());
        messageMapper.updateByPrimaryKeySelective(message);
        return ResultJson.buildSuccess("编辑消息成功");
    }

    private SysMessage convertToMessage(MessageQuery messageQuery, Integer fromUserId) {
        SysMessage sysMessage = new SysMessage();
        sysMessage.setContent(messageQuery.getContent());
        sysMessage.setType(messageQuery.getType());
        sysMessage.setAction(messageQuery.getAction());
        sysMessage.setSourceId(messageQuery.getSourceId());
        sysMessage.setSourceType(messageQuery.getSourceType());
        sysMessage.setTitle(messageQuery.getTitle());
        sysMessage.setCreateTime(new Date());
        sysMessage.setSendTime(messageQuery.getSendTime());
        sysMessage.setCreateBy(fromUserId);
        return sysMessage;
    }


}
