package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "sys_report_record")
public class SysReportRecord {
    @Id
    private Integer id;

    private Integer reportId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "work_id")
    private Integer workId;

    private Date createTime;
}