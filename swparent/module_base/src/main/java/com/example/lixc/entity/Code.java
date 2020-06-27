package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
@ToString
@Table(name = "sys_code")
public class Code {
    private int id;
    private String code;
    @Column(name = "used_num")
    private int usedNum;
    private int createBy;
    private Date createTime;

}