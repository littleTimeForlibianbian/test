package com.example.lixc.service.impl;

import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.entity.SysMessage;
import com.example.lixc.entity.SysUserMessage;
import com.example.lixc.enums.MessageTypeEnum;
import com.example.lixc.mapper.SysMessageMapper;
import com.example.lixc.mapper.SysUserMessageMapper;
import com.example.lixc.service.MessageService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.MessageQuery;
import com.example.lixc.vo.query.UserMessageQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

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
    @Transactional
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
    public SysMessage queryMessage(Integer messageId) {
        //查询消息详情
        return messageMapper.selectByPrimaryKey(messageId);
    }

//    @Override
//    @Transactional
//    public void send(MessageQuery messageQuery) {
//        //插入消息记录表
//        log.info(" send message  start");
//        SysUserMessage userMessage = new SysUserMessage();
//        userMessage.setIsRead("N");
//        userMessage.setToUserId(messageQuery.getToUserId());
//        userMessage.setMessageId(messageQuery.getMessageId());
//        userMessage.setFromUserId(messageQuery.getFromUserId());
//        userMessage.setSendTime(new Date());
//        userMessageMapper.insertSelective(userMessage);
//        log.info(" send message end");
//    }

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
        userMessageQuery.setIsRead("N");
        int loginUserId = SysConfigUtil.getLoginUserId();
        userMessageQuery.setToUserId(loginUserId);
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
    public ResultJson deleteById(Integer messageId) {
        if (messageId <= 0) {
            log.error("消息id为空");
            return ResultJson.buildError("消息id为空");
        }
        //删除消息
        userMessageMapper.deleteByPrimaryKey(messageId);
        return ResultJson.buildSuccess();
    }
}
