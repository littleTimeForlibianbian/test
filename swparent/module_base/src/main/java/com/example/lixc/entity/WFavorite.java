package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Id;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "w_favorite")
public class WFavorite {
    @Id
    private int id;
    private int userId;
    private int targetId;
    private String type;
    private Date createTime;
}
