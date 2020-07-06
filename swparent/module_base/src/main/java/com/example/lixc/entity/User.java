package com.example.lixc.entity;

import com.example.lixc.util.ResultJson;
import com.example.lixc.util.ToolsUtil;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@ToString
@Table(name = "sys_user")
public class User {

    @Id
    private Integer id;

    private Integer roleId;

    private String administrator;

    /**
     * 管理员 所在地
     */
    private String location;

    /**
     * 职位
     */
    private String position;

    /**
     * 真实名称
     */
    private String realName;

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
    private Integer enable;

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


}