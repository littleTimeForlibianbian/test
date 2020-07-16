package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Id;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 22:21
 */
@Data
@Table(name = "w_comment")
public class WComment {
    @Id
    private Integer id;
    private Integer workId;
    private Integer userId;
    private String userName;
    private String content;
    private Integer commentLevel;
    private Integer parentId;
    private Integer topStatus;
    //评论点赞数量
    private Integer praiseNum;
    private Date createTime;


}
