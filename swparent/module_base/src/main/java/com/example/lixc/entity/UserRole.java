package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;

@Data
@ToString
@Table(name = "sys_user_role")
public class UserRole {
    @Id
    private Integer id;

    private Integer userId;

    private Integer roleId;

}