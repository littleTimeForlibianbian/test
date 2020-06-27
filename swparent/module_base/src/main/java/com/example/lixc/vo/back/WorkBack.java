package com.example.lixc.vo.back;

import com.example.lixc.entity.SysImage;
import com.example.lixc.entity.SysWork;
import com.example.lixc.entity.Tag;
import com.example.lixc.entity.User;
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
    //作品id
    private Integer id;
    //作品包含的图片集合
    private List<SysImage> imageList;
    //作品对应的画风
    private List<Tag> tags;
    //作品详情应该包含一个字段 是否被喜欢
    private Boolean isLike;
    //作者信息
    private User user;
}
