package com.example.lixc.vo.query;

import com.example.lixc.common.PageData;
import com.example.lixc.common.PageParam;
import com.example.lixc.entity.Privilege;
import com.example.lixc.util.ResultJson;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author lixc
 * @Description 角色查询实体类
 * @createTime 2020/6/7 22:55
 */
@Data
@ToString
public class RoleQuery extends PageParam {
    private static final long serialVersionUID = 6927024424253723836L;
    private int id;
    private String name;
    private String roleDescription;
    private String enable;
    private String startTime;
    private String endTime;
    private String privilegeIds;

    public ResultJson checkParams() {
        if (StringUtils.isEmpty(name)) {
            return ResultJson.buildError("角色名称不能为空");
        }
        if (StringUtils.isEmpty(privilegeIds)) {
            return ResultJson.buildError("请选择权限");
        }
        return ResultJson.buildSuccess();
    }
}
