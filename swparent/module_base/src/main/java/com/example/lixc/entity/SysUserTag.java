package com.example.lixc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;

@Data
@Table(name = "sys_user_tag")
public class SysUserTag {
    @Id
    private Integer id;

    private Integer userId;

    private Integer tagId;

}