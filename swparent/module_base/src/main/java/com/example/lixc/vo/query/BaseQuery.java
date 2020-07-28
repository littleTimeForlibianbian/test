package com.example.lixc.vo.query;

import com.example.lixc.common.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/7 22:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseQuery extends PageParam {
    private static final long serialVersionUID = -805487982434522852L;
    private String startTime;
    private String endTime;
}
