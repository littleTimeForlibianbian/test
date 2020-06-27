package com.example.lixc.entity;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Table;

@Data
@ToString
@Table(name = "sys_image")
public class SysImage {
    private Integer id;

    private String name;

    private String url;

    private String thumbUrl;

    private Date createTime;

}