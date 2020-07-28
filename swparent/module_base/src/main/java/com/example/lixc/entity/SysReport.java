package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "sys_report")
public class SysReport {
    @Id
    private Integer id;
    //举报条件
    private String content;
    //附加条件1
    private String contentExt1;
    //附加条件2
    private String contentExt2;
    //创建时间
    private Date createTime;
}