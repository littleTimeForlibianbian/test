package com.example.lixc.vo.back;

import com.example.lixc.entity.Role;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lixc
 * @Description 管理员查询实体类
 * @createTime 2020/6/7 15:14
 */
@Data
@ToString
public class AdminUserBack implements Serializable {

    private static final long serialVersionUID = -964116881894622836L;
    private int id;
    private String realName;
    private String nickName;
    private String phone;
    private Role role;
    private String location;
    private String position;
    private String status;
    //
    private String statusCH;
    private String email;
    private String administrator;
    private Date createTime;
    private String enable;
    //是否启用中文
    private String enableCH;
}
