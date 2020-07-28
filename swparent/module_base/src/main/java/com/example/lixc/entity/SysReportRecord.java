package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "sys_report_record")
public class SysReportRecord {
    @Id
    private Integer id;

    private Integer reportId;

    private Integer userId;

    private Date createTime;
}