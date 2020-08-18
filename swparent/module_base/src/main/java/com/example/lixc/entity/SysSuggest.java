package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 11930
 */
@Data
@Table(name = "sys_suggest")
public class SysSuggest {
    @Id
    private Integer id;

    private Integer userId;

    private String userName;

    private String content;

    private Integer version;

    private String url;

    private Date createTime;

    private Integer praiseNum;

    //优先级
    private Integer priority;

    private Integer status;
}