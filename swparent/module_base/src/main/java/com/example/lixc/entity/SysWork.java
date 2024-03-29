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

    private Integer shareNum;

    private Integer recommendNum;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    private String isNormal;
    //失败原因，只有失败时有值
    private String failReason;
    //审核人id
    private Integer checkId;
    //审核时间
    private Date checkTime;

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