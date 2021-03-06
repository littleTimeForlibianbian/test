package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Id;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "sys_message")
public class SysMessage {
    @Id
    private Integer id;

    private Integer type;

    private String action;

    private String title;

    private Integer sourceId;

    private String sourceType;

    private Date createTime;
    
    private Integer createBy;

    private Date sendTime;

    private String content;

}