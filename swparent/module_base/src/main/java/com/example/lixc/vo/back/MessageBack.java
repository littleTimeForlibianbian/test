package com.example.lixc.vo.back;

import com.example.lixc.entity.SysMessage;
import com.example.lixc.entity.SysWork;
import com.example.lixc.entity.SysWorkDict;
import com.example.lixc.entity.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/1 22:28
 */
@Data
public class MessageBack implements Serializable {
    private static final long serialVersionUID = -7911491702950811545L;
    //消息id
    private SysMessage message;
    //发送消息的用户信息
    private User fromUser;
    //推荐部分的作品信息
    private SysWork work;
    //作品所属标签
    private SysWorkDict dicts;


}
