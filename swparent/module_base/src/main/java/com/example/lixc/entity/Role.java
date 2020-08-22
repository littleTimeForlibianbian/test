package com.example.lixc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@ToString
@Table(name = "sys_role")
@JsonIgnoreProperties(value = "handler")
public class Role {
    @Id
    private Integer id;

    private String name;

    private String roleDescription;

    private String enable;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    /**
     * 角色标志 ： user前台用户角色  painter 前台画师角色，null表示后台角色
     */
    private String tag;


    /**
     * 角色类型，1：表示前台角色 2 表示后台角色，前台角色有且只能有一个
     */
    private Integer type;

}