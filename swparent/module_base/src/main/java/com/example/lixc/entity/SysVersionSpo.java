package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
public class SysVersionSpo {
    private Integer id;

    private Integer userId;

    private String content;

    private Date publishTime;

    private Date createTime;

    private Integer version;

    private String type;

    //反馈优化的状态
    private int status;
}