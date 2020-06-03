package com.example.lixc.common;

import com.example.lixc.enums.ResultJsonEnum;
import com.github.pagehelper.Page;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class PageData<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前页数
     */
    private Integer pageNo;
    /**
     * 每页记录数
     */
    private Integer pageSize;
    /**
     * 总记录数
     */
    private Long count;
    /**
     * 结果对象
     */
    private List<T> data = null;

    private Integer status = ResultJsonEnum.SUCCESS_CODE.getCode();

    private String message = ResultJsonEnum.SUCCESS_CODE.getName();

    public PageData(Page<T> page) {
        super();
        this.pageNo = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.count = page.getTotal();
        this.data = page.getResult();
    }

    public PageData(Integer pageNo, Integer pageSize, Long count,
                    List<T> data, Integer status, String message) {
        super();
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.count = count;
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public PageData() {
        super();
    }


}
