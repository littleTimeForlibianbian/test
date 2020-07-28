package com.example.lixc.vo.back;

import com.example.lixc.entity.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author lixc
 * @Description 作品查询返回类
 * @createTime 2020/6/21 10:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WorkBack extends SysWork {

    //作品包含的图片集合
    private List<SysImage> imageList;
    //作品对应的画风
    private List<Tag> tags;
    //作品详情应该包含一个字段 是否被喜欢
    private Boolean isLike;
    //作者信息
//    private User user;

    //如果是推荐的话，记录推荐人的名称
    private String recommendName;

//    private UserAttr userAttr;

}
