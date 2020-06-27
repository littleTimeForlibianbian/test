package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Table;

@Data
@ToString
@Table(name = "sys_work_image")
public class SysWorkImage {
    private Integer id;

    private Integer workId;

    private Integer imageId;

}