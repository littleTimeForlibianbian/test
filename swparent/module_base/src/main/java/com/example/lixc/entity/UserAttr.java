package com.example.lixc.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Id;

import javax.persistence.Table;
import java.util.Date;

@Data
@Alias("userAttr")
@Table(name = "sys_user_attr")
public class UserAttr {
    @Id
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    private String uHistory;
    private String website;
    //头像地址
    @Column(name = "head_image")
    private String headImage;
    private Date createTime;
    //最后一次更新作品时间
    private Date lastPublishTime;
    private Date updateTime;
}
