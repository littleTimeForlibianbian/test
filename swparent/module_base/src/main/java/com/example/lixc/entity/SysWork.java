package com.example.lixc.entity;


import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Id;
import java.util.Date;

@Table(name = "sys_work")
@Data
public class SysWork {
    @Id
    private Integer id;

    private Integer userId;

    private Integer status;

    private String isDelete;

    private String name;

    private String content;

    private Integer praiseNum;

    private Integer commentNum;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    private String isNormal;

    public String getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(String isNormal) {
        this.isNormal = isNormal;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}