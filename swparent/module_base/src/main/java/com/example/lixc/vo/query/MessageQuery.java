package com.example.lixc.vo.query;

import lombok.Data;

import java.util.Date;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/1 11:11
 */
@Data
public class MessageQuery {
    //消息id
    private Integer id;
    //发送者id
    private Integer fromUserId;
    private String fromUserName;
    //接受者id
    private Integer toUserId;
    private String toUserName;
    //类型  '消息类型:announcement公告/remind提醒/message私信',
    private Integer type;
    //动作 '用户动作触发的消息 comment评论，praise点赞，reply回复，recommend推荐 system 系统',
    private String action;
    //消息标题
    private String title;
    //来源id
    private Integer sourceId;
    //来源类型
    private String sourceType;
    //创建时间
    private Date createTime;
    //消息内容
    private String content;
    //是否已读
    private String isRead;
}
