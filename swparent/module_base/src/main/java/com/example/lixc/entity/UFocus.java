package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 21:23
 */
@Data
@Table(name = "u_focus")
public class UFocus {
    @Id
    private Integer id;
    private Integer userId;
    private Integer authorId;
    @Column(name = "is_cancel")
    private String cancel;
    private Date createTime;
}
