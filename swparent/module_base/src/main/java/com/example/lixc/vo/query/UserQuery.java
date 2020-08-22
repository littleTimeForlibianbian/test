package com.example.lixc.vo.query;

import com.example.lixc.common.PageParam;
import com.example.lixc.util.ResultJson;
import com.example.lixc.util.ToolsUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import java.io.File;
import java.util.Date;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/7 15:14
 */
@Data
@Slf4j
@ToString
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户参数类", description = "用户请求参数描述")
public class UserQuery extends PageParam {

    private static final long serialVersionUID = -8825117023632700730L;

    private Integer userID;

    @ApiModelProperty("用户名（邮箱）")
    private String userName;
    /**
     * 昵称 由最多8位中文、英文、下划线、数字组成
     */
    @ApiModelProperty("昵称 由最多8位中文、英文、下划线、数字组成")
    private String nickName;

    /**
     * 由至少8位英文和数字组成
     */
    @ApiModelProperty("密码 由至少8位英文和数字组成")
    private String password;

    /**
     * 验证邮箱格式、验证此邮箱是否注册过。
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 验证手机号格式
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 用戶状态
     */
    private Integer status;

    /**
     * 密码更新日期
     */
    private Date pwdUpdateDate;

    /**
     * 是否开启
     */
    private String enable;

    /**
     * 是否是画师
     */
    private String painter;

    /**
     * 上次登录ip
     */
    private String lastLoginIp;
    /**
     * 用户注册所选城市
     */
    @ApiModelProperty("用户注册所选城市")
    private String city;

    @ApiModelProperty("邀请码")
    private String invitationCode;

    private Integer activeCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private Integer rememberMe;

    private Integer roleId;

    //重置密码token
    private String resetPasswordToken;

    private String uHistory;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    public ResultJson checkParams() {
        if (StringUtils.isEmpty(nickName)) {
            log.error("昵称为空");
            return ResultJson.buildError("昵称为空");
        }
        if (!ToolsUtil.regexNickName(nickName)) {
            log.error("昵称由最多8位中文、英文、下划线、数字组成");
            return ResultJson.buildError("昵称由最多8位中文、英文、下划线、数字组成");
        }
        if (StringUtils.isEmpty(password)) {
            log.error("密码为空");
            return ResultJson.buildError("密码为空");
        }
        //由于前段传入md5数据 此处的密码校验去掉
//        if (!ToolsUtil.regexPass(password)) {
//            return ResultJson.buildError("密码由最多8位中文、英文、下划线、数字组成");
//        }
        if (StringUtils.isEmpty(email)) {
            log.error("邮箱为空");
            return ResultJson.buildError("邮箱为空");
        }
        if (!ToolsUtil.regexEmail(email)) {
            log.error("邮箱格式错误");
            return ResultJson.buildError("邮箱格式错误");
        }
        if (StringUtils.isEmpty(phone)) {
            log.error("手机号为空");
            return ResultJson.buildError("手机号为空");
        }
        if (!ToolsUtil.regexPhone(phone)) {
            log.error("手机号格式错误");
            return ResultJson.buildError("手机号格式错误");
        }
        if (StringUtils.isEmpty(invitationCode)) {
            log.error("邀请码为空");
            return ResultJson.buildError("邀请码为空");
        }
        if (invitationCode.length() != 9) {
            log.error("邀请码错误");
            return ResultJson.buildError("邀请码错误");
        }
        if (StringUtils.isEmpty(city)) {
            log.error("城市为空");
            return ResultJson.buildError("城市为空");
        }
        return ResultJson.buildSuccess();
    }

}
