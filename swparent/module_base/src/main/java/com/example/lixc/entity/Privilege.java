package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@Table(name = "sys_privledge")
public class Privilege {
    @Id
    private Integer id;
    private String name;
    private String url;
    private Integer type;
    private Integer parent;
    //存储所属角色
}