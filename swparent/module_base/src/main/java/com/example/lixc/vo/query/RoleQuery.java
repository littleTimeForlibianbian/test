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
    @ApiModelProperty(value = "启用/停用")
    private String enable;
    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "权限id集合")
    private String privilegeIds;
    /**
     * 角色标志 ： user前台用户角色  painter 前台画师角色，null表示后台角色
     */
    private String tag;
    /**
     * 角色类型，1：表示前台角色 2 表示后台角色，前台角色有且只能有一个
     */
    private Integer type;

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
