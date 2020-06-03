package com.example.lixc.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class SysConfig {

    private Integer id;

    private String invitationCodeOpen;

    private int invitationCodeExpire;

    private Integer updateBy;

    private Date updateTime;

}