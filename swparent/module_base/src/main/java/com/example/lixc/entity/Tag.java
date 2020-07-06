package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;

@Data
@ToString
@Table(name = "sys_tag")
public class Tag {
    @Id
    private Integer id;

    private String type;

    private String content;

    //标签所属分类
    private Integer parentId;

}