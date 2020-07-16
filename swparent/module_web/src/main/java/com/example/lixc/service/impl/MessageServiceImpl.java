package com.example.lixc.service.impl;

import com.example.lixc.entity.SysMessage;
import com.example.lixc.enums.MessageTypeEnum;
import com.example.lixc.mapper.SysMessageMapper;
import com.example.lixc.mapper.SysUserMessageMapper;
import com.example.lixc.service.MessageService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.MessageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lixc
 * @Description 消息服务service
 * @createTime 2020/7/1 11:07
 */
@Service
public class MessageServiceImpl implements MessageService {


    @Autowired
    private SysMessageMapper messageMapper;

    @Autowired
    private SysUserMessageMapper userMessageMapper;

    @Override
    @Transactional
    public void create(MessageQuery messageQuery) {
        SysMessage message = changeToMessage(messageQuery);
        messageMapper.insertSelective(message);
    }

    private SysMessage changeToMessage(MessageQuery messageQuery) {
        SysMessage message = new SysMessage();
        message.setCreateTime(new Date());
        message.setTitle(messageQuery.getTitle());
        message.setSourceType(messageQuery.getSourceType());
        message.setSourceId(messageQuery.getSourceId());
        message.setAction(messageQuery.getAction());
        message.setType(messageQuery.getType());
        //系统后台添加的文章
        if (MessageTypeEnum.MESSAGE_TYPE_ANN.getCode() == (messageQuery.getType())) {
            message.setContent(messageQuery.getContent());
        }
        //TODO 其余的消息内容需要自己组装
        return message;
    }

    @Override
    public SysMessage queryMessage(MessageQuery messageQuery) {
        return null;
    }

    @Override
    @Transactional
    public void send(MessageQuery messageQuery) {
        //插入消息记录表
    }

    @Override
    public List<SysMessage> queryNotRead(MessageQuery messageQuery) {
        //查询所有的未读消息
        return null;
    }

    @Override
    public List<SysMessage> querySystem(MessageQuery messageQuery) {
        //查询所有的系统消息
        return null;
    }

    @Override
    public List<SysMessage> queryDynamic(MessageQuery messageQuery) {
        //查询所有的动态消息
        return null;
    }

    @Override
    public void deleteById(MessageQuery messageQuery) {
        //删除消息
    }
}
