package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Id;

@Data
@Table(name = "sys_user_tag")
public class SysUserTag {
    @Id
    private Integer id;

    private Integer userId;

    private Integer tagId;

}