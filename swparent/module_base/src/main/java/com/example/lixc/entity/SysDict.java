package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;


@Data
@ToString
@Table(name = "sys_dict")
public class SysDict {
    @Id
    private Integer id;

    private String dictKey;

    private String dictValue;

    private Integer pId;

}