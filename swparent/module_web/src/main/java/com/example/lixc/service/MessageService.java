package com.example.lixc.service;

import com.example.lixc.entity.SysMessage;
import com.example.lixc.vo.query.MessageQuery;

import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/1 11:06
 */
public interface MessageService {

    //创建消息
    void create(MessageQuery messageQuery);

    //查询消息
    SysMessage queryMessage(MessageQuery messageQuery);

    //发送消息
    void send(MessageQuery messageQuery);

    //查询我的未读消息
    List<SysMessage> queryNotRead(MessageQuery messageQuery);

    //查询系统消息
    List<SysMessage> querySystem(MessageQuery messageQuery);

    //查询动态消息
    List<SysMessage> queryDynamic(MessageQuery messageQuery);

    //删除系统消息
    void deleteById(MessageQuery messageQuery);


}
