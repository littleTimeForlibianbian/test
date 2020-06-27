package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Table;


@Data
@ToString
@Table(name = "sys_dict")
public class SysDict {
    private Integer id;

    private String dictKey;

    private String dictValue;

    private Integer pId;

}