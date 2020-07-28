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

    /**
     * 发送消息
     *
     * @param messageQueryList messageQuery集合对象
     * @param isTimed      是否是定时发送，y:是  n:false
     */
    void create(List<MessageQuery> messageQueryList, boolean isTimed);

    //查询消息
    SysMessage queryMessage(MessageQuery messageQuery);

    //发送消息
    void send(MessageQuery messageQuery);

    //查询我的未读消息
    List<SysMessage> queryNotRead(MessageQuery messageQuery);

    int queryNotReadCount(MessageQuery messageQuery);

    //查询系统消息
    List<SysMessage> querySystem(MessageQuery messageQuery);

    //查询动态消息
    List<SysMessage> queryDynamic(MessageQuery messageQuery);

    //删除系统消息
    void deleteById(MessageQuery messageQuery);


}
