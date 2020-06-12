package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 登录记录表
 */
@Data
@ToString
@Table(name = "sys_login_record")
public class LoginRecord {
    @Id
    private int id;
    private int userId;
    private String userName;
    private String loginIp;
    private String loginAreaCode;
    private Date createTime;

}
