package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
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
    @Column(name = "user_id")
    private int userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "login_ip")
    private String loginIp;
    @Column(name = "login_area_code")
    private String loginAreaCode;
    @Column(name = "create_time")
    private Date createTime;

}
