package com.example.lixc.vo.back;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @className: CommentBack
 * @description: 评论查询结果返回类
 * @Author: Wilson
 * @createTime 2020/7/30 11:06
 */
@Data
@ToString
public class CommentBack implements Serializable {
    private static final long serialVersionUID = -3192472545313689522L;
    //评论id
    private Integer commentId;
    //评论内容
    private String content;

    //评论的目标ID  对于作品来说就是作品Id
    private Integer targetId;

    private String targetType;

    //评论等级
    private Integer commentLevel;

    //如果是二级评论，则需要这个parentId
    private Integer parentId;

    private Integer topStatus;

    private Integer praiseNum;

    //评论时间
    private Date createTime;
    //评论人 id
    private Integer userId;
    //评论人 昵称
    private String nickName;
    //评论人头像url
    private String headImageUrl;
}
