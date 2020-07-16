package com.example.lixc.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Id;

import javax.persistence.Table;
import java.util.Date;

@Data
@Alias("userAttr")
@Table(name = "sys_user_attr")
public class UserAttr {
    @Id
    private Integer id;
    private Integer userId;
    private String uHistory;
    private String website;
    private String headImage;
    private Date createTime;
}
