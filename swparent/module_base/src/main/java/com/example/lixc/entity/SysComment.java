package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@ToString
@Table(name = "sys_comment")
public class SysComment {
    @Id
    private Integer id;

    private Integer targetId;

    private String targetType;

    @Column(name = "user_id")
    private Integer userId;

    private String content;

    private Integer commentLevel;

    private Integer parentId;

    private Integer topStatus;

    private Integer praiseNum;

    private Date createTime;
}