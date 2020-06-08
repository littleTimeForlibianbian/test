package com.example.lixc.vo.query;

import com.example.lixc.util.ResultJson;
import com.example.lixc.util.ToolsUtil;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/7 15:14
 */
@Data
@ToString
public class UserQuery {
    private Integer id;

    private String userName;
    /**
     * 昵称 由最多8位中文、英文、下划线、数字组成
     */
    private String nickName;

    /**
     * 由至少8位英文和数字组成
     */
    private String password;

    /**
     * 验证邮箱格式、验证此邮箱是否注册过。
     */
    private String email;

    /**
     * 验证手机号格式
     */
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
    private String city;

    private String invitationCode;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    public ResultJson checkParams() {
        if (StringUtils.isEmpty(nickName)) {
            return ResultJson.buildError("昵称为空");
        }
        if (!ToolsUtil.regexNickName(nickName)) {
            return ResultJson.buildError("由最多8位中文、英文、下划线、数字组成");
        }
        if (StringUtils.isEmpty(password)) {
            return ResultJson.buildError("密码为空");
        }
        if (!ToolsUtil.regexPass(password)) {
            return ResultJson.buildError("由最多8位中文、英文、下划线、数字组成");
        }
        if (StringUtils.isEmpty(email)) {
            return ResultJson.buildError("邮箱为空");
        }
        if (!ToolsUtil.regexEmail(email)) {
            return ResultJson.buildError("邮箱格式错误");
        }
        if (StringUtils.isEmpty(phone)) {
            return ResultJson.buildError("手机号为空");
        }
        if (!ToolsUtil.regexPhone(phone)) {
            return ResultJson.buildError("手机号格式错误");
        }
        if (!ToolsUtil.regexPhone(invitationCode)) {
            return ResultJson.buildError("邀请码为空");
        }
        if (invitationCode.length() != 9) {
            return ResultJson.buildError("邀请码错误");
        }
        if (!ToolsUtil.regexPhone(city)) {
            return ResultJson.buildError("城市为空");
        }
        return ResultJson.buildSuccess();
    }

}
