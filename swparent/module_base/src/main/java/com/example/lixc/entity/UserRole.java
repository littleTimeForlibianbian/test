package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Table;

@Data
@ToString
@Table(name = "sys_user_role")
public class UserRole {
    private Integer id;

    private Integer userId;

    private Integer roleId;

}