package com.example.lixc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@ToString
@Table(name = "sys_privilege")
@JsonIgnoreProperties(value = "handler")
public class Privilege implements Serializable {
    private static final long serialVersionUID = -3365976592896058118L;
    @Id
    private Integer id;
    private String name;
    private String url;
    private Integer type;
    private Integer parent;
}