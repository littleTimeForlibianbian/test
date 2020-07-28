package com.example.lixc.vo.back;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Wilson
 * @Description
 * @createTime 2020/7/14 10:50
 */
@Data
@ApiModel(description = "用户属性的详细信息")
public class UserAttrBack implements Serializable {

    private static final long serialVersionUID = 2841541079174851625L;
    @Id
    @ApiModelProperty(value = "主键id")
    @GeneratedValue(generator = "JDBC")
    private Integer userAttrId;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @Column(name = "u_history")
    @ApiModelProperty(value = "创作历史")
    private String uHistory;

    @Column(name = "website")
    @ApiModelProperty(value = "常用网站")
    private String website;

    @Column(name = "head_image")
    @ApiModelProperty(value = "用户头像路径")
    private String headImage;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
