package com.example.lixc.common;

import java.io.Serializable;

/**
 * @Description 分页请求参数实体
 */
public class PageParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前页数
     */
    private Integer pageNo = 1;

    /**
     * 每页记录数
     */
    private Integer pageSize = 10;


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    public void manualPaging() {
        this.pageNo = null;
        this.pageSize = null;
    }

    public String getSqlLimit() {
        if (pageNo != null && pageSize != null) {
            return "limit " + (pageNo - 1) * pageSize + "," + pageSize;
        }
        return "limit " + (pageNo - 1) * pageSize + "," + pageSize;
    }
}
