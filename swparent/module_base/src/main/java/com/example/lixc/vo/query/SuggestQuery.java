package com.example.lixc.vo.query;

import com.example.lixc.common.PageParam;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/6 23:20
 */

@Data
@ToString
public class SuggestQuery extends PageParam {
    private Integer suggestId;
    private Integer userId;
    private String startTime;
    private String endTime;
    private String key;

}
