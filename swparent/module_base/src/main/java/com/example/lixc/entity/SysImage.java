package com.example.lixc.entity;

import java.util.Date;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Alias("sysImage")
@ToString
@Table(name = "sys_image")
public class SysImage {

    @Id
    private Integer id;

    private String name;

    private String url;

    private String thumbUrl;

    private Date createTime;

}