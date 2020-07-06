package com.example.lixc.service.impl;

import com.example.lixc.entity.SysMessage;
import com.example.lixc.mapper.SysMessageMapper;
import com.example.lixc.mapper.SysUserMessageMapper;
import com.example.lixc.service.MessageService;
import com.example.lixc.vo.query.MessageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    }

    @Override
    public SysMessage queryMessage(MessageQuery messageQuery) {
        return null;
    }

    @Override
    @Transactional
    public void send(MessageQuery messageQuery) {

    }

    @Override
    public List<SysMessage> queryNotRead(MessageQuery messageQuery) {
        return null;
    }

    @Override
    public List<SysMessage> querySystem(MessageQuery messageQuery) {
        return null;
    }

    @Override
    public List<SysMessage> queryDynamic(MessageQuery messageQuery) {
        return null;
    }
}
