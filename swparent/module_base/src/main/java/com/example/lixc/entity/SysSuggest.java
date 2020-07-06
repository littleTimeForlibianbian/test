package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "sys_suggest")
public class SysSuggest {
    @Id
    private Integer id;

    private Integer userId;

    private String content;

    private Integer version;

    private String url;

    private Date createTime;

    private Integer praiseNum;

}