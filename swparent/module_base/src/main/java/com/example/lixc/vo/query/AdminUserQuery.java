package com.example.lixc.vo.query;

import com.example.lixc.common.PageParam;
import com.example.lixc.util.ResultJson;
import com.example.lixc.util.ToolsUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/7 15:56
 */
@Data()
@ToString
@ApiModel(value = "管理员参数类", description = "管理员请求参数描述")
public class AdminUserQuery extends PageParam {
    private static final long serialVersionUID = 631833414854826192L;
    @Id
    private int id;
    @ApiModelProperty("开始时间")
    //开始时间
    private String startTime;
    //结束时间
    @ApiModelProperty("结束时间")
    private String endTime;
    //用户名
    @ApiModelProperty("用户名（邮箱）")
    private String email;
    //真是姓名
    @ApiModelProperty("真实姓名")
    private String realName;
    //手机号
    @ApiModelProperty("手机号")
    private String phone;
    //职位
    @ApiModelProperty("职位")
    private String position;
    //所在地
    @ApiModelProperty("所在地")
    private String location;
    //角色
    @ApiModelProperty("角色id")
    private int roleId;
    //密码
    @ApiModelProperty("密码")
    private String password;

    private Date createTime;


    public ResultJson checkParams() {
        if (StringUtils.isEmpty(email)) {
            return ResultJson.buildError("用户名为空");
        }
        if (!ToolsUtil.regexEmail(email)) {
            return ResultJson.buildError("邮箱格式错误");
        }
//        if (StringUtils.isEmpty(realName)) {
//            return ResultJson.buildError("真实姓名为空");
//        }
        if (StringUtils.isEmpty(phone)) {
            return ResultJson.buildError("手机号为空");
        }
        if (!ToolsUtil.regexPhone(phone)) {
            return ResultJson.buildError("手机号格式错误");
        }
        if (StringUtils.isEmpty(position)) {
            return ResultJson.buildError("职位为空");
        }
        if (StringUtils.isEmpty(location)) {
            return ResultJson.buildError("所在地为空");
        }
        if (roleId <= 0) {
            return ResultJson.buildError("角色为空");
        }
        if (StringUtils.isEmpty(password)) {
            return ResultJson.buildError("密码为空");
        }
        if (!ToolsUtil.regexPass(password)) {
            return ResultJson.buildError("由最多8位中文、英文、下划线、数字组成");
        }
        return ResultJson.buildSuccess();
    }

}
