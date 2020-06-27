package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Table;

@Data
@ToString
@Table(name = "sys_tag")
public class Tag {
    private Integer id;

    private String type;

    private String content;

}