package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "w_favorite")
public class WFavorite {
    @Id
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "target_id")
    private int targetId;
    //点赞的类型，作品为work，评论为comment
    private String type;
    private Date createTime;
}
