package com.example.lixc.vo.back;

import com.example.lixc.entity.SysMessage;
import com.example.lixc.entity.SysWork;
import com.example.lixc.entity.SysWorkDict;
import com.example.lixc.entity.User;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/1 22:28
 */
public class MessageBack {
    //消息id
    private SysMessage message;
    //发送消息的用户信息
    private User fromUser;
    //推荐部分的作品信息
    private SysWork work;
    //作品所属标签
    private SysWorkDict dicts;


}