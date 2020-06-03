package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 登录记录表
 */
@Data
@ToString
public class LoginRecord {
    private int id;
    private int userId;
    private String nickName;
    private String loginIp;
    private String loginAreaCode;
    private Date createTime;

}
