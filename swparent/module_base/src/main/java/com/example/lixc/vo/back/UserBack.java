package com.example.lixc.vo.back;

import com.example.lixc.entity.Role;
import com.example.lixc.entity.Tag;
import com.example.lixc.entity.UserAttr;
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
    private String realName;
    private String email;
    private String location;
    //用户所在城市中文
    private String locationCN;
    private String phone;
    private Date createTime;
    private Date lastLoginTime;
    private String enable;
    private String password;
    private String painter;
    private List<Role> roleList;
    private List<Tag> tagList;
    private UserAttr userAttr;

}
