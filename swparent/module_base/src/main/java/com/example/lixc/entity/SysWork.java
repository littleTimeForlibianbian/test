package com.example.lixc.entity;


import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "sys_work")
@Data
public class SysWork {
    private Integer id;

    private Integer userId;

    private Integer status;

    private String isDelete;

    private String name;

    private String content;

    private String workStyle;

    private String workCategory;

    private Integer praiseNum;

    private Integer commentNum;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}