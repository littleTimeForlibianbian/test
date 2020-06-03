package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Role {
    private Integer id;

    private String name;

    private String isSuper;

    private String roleDescription;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

}