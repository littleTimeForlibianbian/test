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

}