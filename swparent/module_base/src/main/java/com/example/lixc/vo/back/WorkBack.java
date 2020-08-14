package com.example.lixc.vo.back;

import com.example.lixc.entity.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author lixc
 * @Description 作品查询返回类
 * @createTime 2020/6/21 10:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WorkBack extends SysWork implements Serializable {

    private static final long serialVersionUID = -5622531474420588562L;
    //作品包含的图片集合
    private List<SysImage> imageList;
    //作品对应的画风
    private List<SysDict> dicts;
    //作品详情应该包含一个字段 是否被喜欢
    private Boolean isLike;

    //作者信息
    private UserBack user;

    //如果是推荐的话，记录推荐人的名称
    private String recommendName;

    //推荐人您呈
    private String nickName;
    //推荐人头像
    private String userHeadImage;
//    private String email;
//    private String uHistory;
//    private String website;
//    private Integer city;
//    private List<Tag> tags;

//    private UserAttr userAttr;

}
