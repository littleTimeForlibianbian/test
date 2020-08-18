package com.example.lixc.vo.query;

import com.example.lixc.common.PageParam;
import com.example.lixc.util.ToolsUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author lixc
 * @Description 建议反馈相关接口请求参数封装类
 * @createTime 2020/7/6 23:20
 */

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class SuggestQuery extends PageParam {

    private static final long serialVersionUID = -1809280834604819894L;

    private Integer suggestId;

    private Integer userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 排序字段   为字段名+空格+排序规则
     * praiseNum[空格]+
     * +表示升序，-表示降序
     */
    private String key;

    public String getKey() {
        if (StringUtils.isEmpty(key)) {
            return "";
        }
        key = ToolsUtil.humpToLine2(key);
        key = key.replace("-", " desc,").replace("+", " asc,");
        key = key.substring(0, key.length() - 1);
        return key;
    }
}
