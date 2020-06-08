package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RolePrivilege {
    private Integer id;

    private Integer roleId;

    private Integer pId;

}