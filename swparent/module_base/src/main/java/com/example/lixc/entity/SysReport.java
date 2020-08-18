package com.example.lixc.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @className: ReportQuery
 * @description: 举报表对应实体类
 * @Author: Wilson
 * @createTime 2020/8/16 15:02
 */
@Data
@Table(name = "sys_report")
public class SysReport {
    @Id
    private Integer id;
    /**
     * 举报条件
     */
    private String content;
    /**
     * 附加条件1
     */
    private String contentExt;
    /**
     * 创建时间
     */
    private Date createTime;
}