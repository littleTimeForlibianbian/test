package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@ToString
@Table(name = "sys_config")
public class SysConfig {
    @Id
    private Integer id;

    private String invitationCodeOpen;

    private int invitationCodeExpire;

    private Integer updateBy;

    private Date updateTime;

}