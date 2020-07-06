package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;

@Data
@ToString
@Table(name = "sys_work_dict")
public class SysWorkDict {
    @Id
    private Integer id;

    private Integer workId;

    private Integer dictId;
}