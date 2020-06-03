package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Code {
    private int id;
    private String code;
    private int usedNum;
    private int createBy;
    private Date createTime;

}