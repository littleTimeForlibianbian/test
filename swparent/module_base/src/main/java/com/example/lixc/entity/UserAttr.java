package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name="sys_user_attr")
public class UserAttr {
    private Integer id;
    private Integer userId;
    private String uHistory;
    private String website;
    private Date createTime;
}
