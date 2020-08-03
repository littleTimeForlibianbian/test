package com.example.lixc.vo.query;

import lombok.Data;

/**
 * @className: UserMessageQuery
 * @description: 用户消息查询实体类
 * @Author: Wilson
 * @createTime 2020/7/29 13:49
 */
@Data
public class UserMessageQuery {
    //消息id
    private int messageId;
    //是否已读
    private String isRead;
    //消息类型
    private int type;
    //消息产生的动作场景
    private String action;
    //消息接受者
    private int toUserId;
}
