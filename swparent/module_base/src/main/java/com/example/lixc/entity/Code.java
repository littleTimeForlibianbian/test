package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 11930
 */
@Data
@ToString
@Table(name = "sys_code")
public class Code {
    @Id
    private Integer id;
    private String code;
    @Column(name = "used_num")
    private Integer usedNum;
    private Date createTime;

}