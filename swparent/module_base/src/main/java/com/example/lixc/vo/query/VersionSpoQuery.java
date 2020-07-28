package com.example.lixc.vo.query;

import lombok.Data;

import java.util.Date;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/7 22:19
 */
@Data
public class VersionSpoQuery extends BaseQuery {

    private int id;
    //用户id
    private Integer userId;
    //版本剧透内容
    private String content;
    //预发布时间
    private Date publishTime;

    private String type;
    //当前版本
    private int version;

    private int status;


}
