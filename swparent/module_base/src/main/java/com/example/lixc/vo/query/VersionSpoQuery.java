package com.example.lixc.vo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/7 22:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class VersionSpoQuery extends BaseQuery {

    private static final long serialVersionUID = 8391393925525851314L;

    private int id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 版本剧透内容
     */
    private String content;
    /**
     * 预发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishTime;
    /**
     * 版本剧透spo  反馈优化opt
     */
    private String type;
//    /**
//     * 当前版本
//     */
//    private int version;
    /**
     * 优化状态 1待优化  2 优化中  3 优化完毕
     */
    private int status;


}
