package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "sys_user_message")
public class SysUserMessage {
    @Id
    private Integer id;

    private Integer messageId;

    private Date sendTime;

    private Integer fromUserId;

    private Integer toUserId;

    private String isRead;

}