package com.example.lixc.vo.back;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lixc
 * @Description 前台用户查询实体类
 * @createTime 2020/6/7 16:22
 */
@Data
@ToString
public class UserBack implements Serializable {
    private int id;
    private String nickName;
    private String email;
    private String location;
    private String phone;
    private Date createTime;
    private Date lastLoginTime;
    private int enable;
}
