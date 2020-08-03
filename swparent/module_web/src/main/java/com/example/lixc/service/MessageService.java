package com.example.lixc.service;

import com.example.lixc.entity.SysMessage;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.MessageQuery;
import com.example.lixc.vo.query.UserMessageQuery;

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
     * @param messageQuery messageQuery对象
     * @param fromUserId   发送者id
     * @param toUserIdList 接受者id集合
     * @param isTimed      是否是定时发送，y:是  n:false
     */
    void create(MessageQuery messageQuery, Integer fromUserId, List<Integer> toUserIdList, boolean isTimed);

    //查询消息
    SysMessage queryMessage(Integer messageId);

//    //发送消息
//    void send(MessageQuery messageQuery);

    //查询我的未读消息
    List<SysMessage> queryNotRead(UserMessageQuery userMessageQuery);

    int queryNotReadCount(UserMessageQuery userMessageQuery);

    //查询系统消息
    List<SysMessage> querySystem(UserMessageQuery userMessageQuery);

    //查询动态消息
    List<SysMessage> queryDynamic(UserMessageQuery userMessageQuery);

    //删除系统消息
    ResultJson deleteById(Integer messageId);


}
