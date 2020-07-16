package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import javax.persistence.Id;

import javax.persistence.Table;


@Data
@ToString
@Table(name = "sys_dict")
@Alias("sysDict")
public class SysDict {
    @Id
    private Integer id;

    private String dictKey;

    private String dictValue;

    private Integer pId;

}