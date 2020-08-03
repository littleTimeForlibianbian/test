package com.example.lixc.vo.back;

import lombok.Data;

import java.util.Date;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/7 22:59
 */
@Data
public class VersionSpoBack {
    private int versionSpoId;
    private String userName;
    private String healImage;
    private int userId;
    private String content;
    private Date createTime;
    private Date publishTime;
    // 0 待优化  1优化中  2优化完毕
    private int status;
    private String statusCH;
}
