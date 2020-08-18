package com.example.lixc.service;

import com.example.lixc.entity.SysMessage;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.MessageBack;
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

    /**
     * 查询消息
     *
     * @param messageId 消息id
     * @return
     */
    MessageBack queryMessage(Integer messageId);

    /**
     * 单独发送消息
     *
     * @param messageQuery 消息查询参数封装类
     */
    void send(MessageQuery messageQuery);

    /**
     * 查询我的未读消息
     *
     * @param userMessageQuery 用户消息参数封装类
     * @return
     */
    List<SysMessage> queryNotRead(UserMessageQuery userMessageQuery);

    int queryNotReadCount(UserMessageQuery userMessageQuery);

    /**
     * 查询系统消息关联用户
     *
     * @param userMessageQuery 用户消息参数封装类
     * @return
     */
    List<SysMessage> querySystem(UserMessageQuery userMessageQuery);

    /**
     * 查询动态消息
     *
     * @param userMessageQuery 用户消息参数封装类
     * @return
     */
    List<SysMessage> queryDynamic(UserMessageQuery userMessageQuery);

    /**
     * 删除系统消息
     *
     * @param messageId 消息id
     * @return
     */
    ResultJson deleteById(Integer messageId);

    /**
     * 查询系统消息，管理后台
     *
     * @param messageQuery 消息查询参数封装类
     * @return
     */
    List<MessageBack> querySysMessage(MessageQuery messageQuery);

    /**
     * 添加系统消息
     *
     * @param messageQuery 消息查询参数封装类
     * @return
     */
    ResultJson addMessage(MessageQuery messageQuery);

    /**
     * 编辑系统消息
     *
     * @param messageQuery  消息查询参数封装类
     * @return
     */
    ResultJson edit(MessageQuery messageQuery);

    /**
     * 批量删除 系统消息
     *
     * @param split
     */
//    void deleteBatch(String[] split);
}
