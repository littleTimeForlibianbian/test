package com.example.lixc.vo.query;

import com.example.lixc.common.PageParam;
import com.example.lixc.util.ResultJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lixc
 * @Description 角色查询实体类
 * @createTime 2020/6/7 22:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@ApiModel(value = "角色请求参数类", description = "角色请求参数描述")
public class RoleQuery extends PageParam {

    private static final long serialVersionUID = 6927024424253723836L;

    @ApiModelProperty(value = "角色id")
    private int id;

    @ApiModelProperty(value = "角色名称")
    private String name;
    @ApiModelProperty(value = "角色描述")
    private String roleDescription;
    private String enable;
    private String startTime;
    private String endTime;
    @ApiModelProperty(value = "权限id集合")
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
