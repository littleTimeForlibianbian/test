package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@Table(name = "sys_role_privledge")
public class RolePrivilege {
    @Id
    private Integer id;

    private Integer roleId;

    private Integer pId;

}