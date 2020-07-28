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

    @Async
    @Override
    @Transactional
    public void create(List<MessageQuery> messageQueryList, boolean isTimed) {
        try {
            if (!CollectionUtils.isEmpty(messageQueryList)) {
                if (messageQueryList.size() == 1) {
                    log.info("单点发送消息");
                }
                for (MessageQuery messageQuery : messageQueryList) {
                    log.info("生成消息记录start");
                    SysMessage message = changeToMessage(messageQuery);
                    messageMapper.insertUseGeneratedKeys(message);
                    log.info("生成消息记录end");
                    if (!isTimed) {
                        log.info("非定时发送");
                        log.info(" send message  start");
                        SysUserMessage userMessage = new SysUserMessage();
                        userMessage.setIsRead("N");
                        userMessage.setToUserId(messageQuery.getToUserId());
                        userMessage.setMessageId(message.getId());
                        userMessage.setFromUserId(messageQuery.getFromUserId());
                        userMessage.setSendTime(new Date());
                        userMessageMapper.insertSelective(userMessage);
                        log.info(" send message end");
                    } else {
                        //TODO  定时发送
                    }
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
        message.setToUserId(messageQuery.getToUserId());
        message.setFromUserId(messageQuery.getFromUserId());
        return message;
    }

    @Override
    public SysMessage queryMessage(MessageQuery messageQuery) {
        //查询消息详情
        return messageMapper.selectByPrimaryKey(messageQuery.getMessageId());
    }

    @Override
    @Transactional
    public void send(MessageQuery messageQuery) {
        //插入消息记录表
        log.info(" send message  start");
        SysUserMessage userMessage = new SysUserMessage();
        userMessage.setIsRead("N");
        userMessage.setToUserId(messageQuery.getToUserId());
        userMessage.setMessageId(messageQuery.getMessageId());
        userMessage.setFromUserId(messageQuery.getFromUserId());
        userMessage.setSendTime(new Date());
        userMessageMapper.insertSelective(userMessage);
        log.info(" send message end");
    }

    @Override
    public List<SysMessage> queryNotRead(MessageQuery messageQuery) {
        int loginUserId = SysConfigUtil.getLoginUserId();
        messageQuery.setToUserId(loginUserId);
        messageQuery.setIsRead("N");
        //查询所有的未读消息
        return userMessageMapper.query(messageQuery);
    }

    @Override
    public int queryNotReadCount(MessageQuery messageQuery) {
        int loginUserId = SysConfigUtil.getLoginUserId();
        messageQuery.setToUserId(loginUserId);
        messageQuery.setIsRead("N");
        //查询所有的未读消息
        return userMessageMapper.queryCount(messageQuery);
    }

    @Override
    public List<SysMessage> querySystem(MessageQuery messageQuery) {
        //查询所有的系统消息
        messageQuery.setType(MessageTypeEnum.MESSAGE_TYPE_ANN.getCode());
        messageQuery.setIsRead("N");
        int loginUserId = SysConfigUtil.getLoginUserId();
        messageQuery.setToUserId(loginUserId);
        return userMessageMapper.query(messageQuery);
    }

    @Override
    public List<SysMessage> queryDynamic(MessageQuery messageQuery) {
        //查询所有的系统消息
        messageQuery.setType(MessageTypeEnum.MESSAGE_TYPE_REM.getCode());
        messageQuery.setIsRead("N");
        int loginUserId = SysConfigUtil.getLoginUserId();
        messageQuery.setToUserId(loginUserId);
        return null;
    }

    @Override
    public void deleteById(MessageQuery messageQuery) {
        //删除消息
    }
}
