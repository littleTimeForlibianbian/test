package com.example.lixc.vo.back;

import com.example.lixc.entity.Role;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lixc
 * @Description 前台用户查询实体类
 * @createTime 2020/6/7 16:22
 */
@Data
@ToString
public class UserBack implements Serializable {
    private static final long serialVersionUID = -6810545859574328877L;
    private int id;
    private String nickName;
    private String email;
    private String location;
    private String phone;
    private Date createTime;
    private Date lastLoginTime;
    private int enable;
    private String password;
    private List<Role> roleList;

}
