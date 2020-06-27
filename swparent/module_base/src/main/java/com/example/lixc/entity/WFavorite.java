package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "w_favorite")
public class WFavorite {
    private int id;
    private int userId;
    private int workId;
    private Date createTime;
}