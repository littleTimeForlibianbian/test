package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@ToString
@Table(name = "sys_policy")
public class Policy {
    @Id
    private Integer id;

    // 协议类型  use 使用协议   private 隐私协议
    private String type;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    private String content;

}