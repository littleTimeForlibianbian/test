package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Tag {
    private Integer id;

    private String type;

    private String content;

}