package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Privilege {
    private Integer id;
    private String name;
    private String url;
    private Integer type;
    private Integer parent;

}