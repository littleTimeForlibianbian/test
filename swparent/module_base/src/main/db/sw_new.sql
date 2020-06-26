-- 用户标签表 用来存储用户选择的标签
drop table if exists `sys_user_tag`;
create table `sys_user_tag`
(
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- 作品表
drop table if exists `sys_work`;
create table `sys_work`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id`     int(11)      NOT NULL COMMENT '作者id',
    `status`      int(1)       NOT NULL COMMENT '审核状态',
    `is_delete`   char(1)      not null default 'N' comment '是否删除',
    `name`        varchar(255) not null comment '作品名称',
    `content`     varchar(255) null comment '作品内容/作品描述',
#     `work_style`    varchar(255) not null comment '画风',
#     `work_category` varchar(255) not null comment '品类',
    `praise_num`  int(11)      NOT NULL DEFAULT 0 COMMENT '点赞数',
    `comment_num` int(11)      NOT NULL DEFAULT 0 COMMENT '评论数',
    `create_by`   int(11)      NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL DEFAULT now() COMMENT '创建时间',
    `update_by`   int(11)               DEFAULT NULL COMMENT '更新人',
    `update_time` datetime              DEFAULT NULL COMMENT '更新时间',

    PRIMARY KEY (`id`)
) engine = InnoDB
  default charset = utf8;
# 作品字典表
drop table if exists `sys_work_dict`;
create table `sys_work_dict`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `work_id` int(11) NOT NULL COMMENT '作品id',
    `dict_id` int(11) not null comment '字典id',
    PRIMARY KEY (`id`)
) engine = InnoDB comment '作品字典关联表'
  default charset = utf8;


# # 作品品类表
# drop table if exists `sys_work_category`;
# create table `sys_work_category`
# (
#     `id`      int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
#     `work_id` int(11) NOT NULL COMMENT '作品id',
#     `dict_id` int(11) not null comment '画风品类的字典id'
# ) engine = InnoDB
#   default charset = utf8;


-- 上传图片表
drop table if exists `sys_image`;
create table `sys_image`
(
    `id`          int(11)      not null auto_increment comment '主键id',
    `name`        varchar(255) null comment '图片名称',
    `url`         varchar(255) not null comment '图片存储路径',
    `thumb_url`   varchar(255) not null comment '缩略图存储路径',
    `create_time` datetime     NULL COMMENT '创建时间',
    primary key (`id`)
) Engine = InnoDB
  default charset = utf8;

-- 作品图片关联表
drop table if exists `sys_work_image`;
create table `sys_work_image`
(
    id         int(11) not null auto_increment comment '主键id',
    `work_id`  int(11) not null comment '作品id',
    `image_id` int(11) not null comment '图片id',
    primary key (`id`)
) Engine = InnoDB
  default charset = utf8;


--  点赞记录表/喜欢的作品
drop table if exists `w_favorite`;
create table `w_favorite`
(
    `id`          int(10)  NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id`     int(10)  NOT NULL COMMENT '用户id',
    `work_id`     int(10)  NOT NULL COMMENT '作品id',
    `create_time` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- 作品评论表
drop table if exists `w_comment`;
create table `w_comment`
(
    `id`            int(11)      NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `work_id`       int(11)      NOT NULL COMMENT '作品id',
    `user_id`       int(11)      NOT NULL COMMENT '评论人id',
    `user_name`     varchar(255) null comment '评论人昵称',
    `content`       varchar(255) null comment '评论内容',
#  `answer_id` int(10)  NULL    COMMENT '答复人id',
    `comment_level` tinyint(4)   NOT NULL DEFAULT '1' COMMENT '评论等级[ 1 一级评论 默认 ，2 二级评论]',
    `parent_id`     int(11)      NULL COMMENT '父级id',
    `top_status`    tinyint(4)   NOT NULL DEFAULT 0 COMMENT '置顶状态[ 1 置顶，0 不置顶 默认 ]',
    `praise_num`    int(11)      NOT NULL DEFAULT '0' COMMENT '点赞数',
    `create_time`   datetime     NOT NULL DEFAULT now() COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

## 如果直接点击评论 则parentid为0  如果点击回复，则parentid为评论id
#A评论very good 1表示A
insert into w_comment (work_id, user_id, parent_id, praise_num, create_time, content)
VALUES (1, 1, 0, 0, now(), 'very good');
#B回复 nice 2表示B
insert into w_comment (work_id, user_id, parent_id, praise_num, create_time, content)
VALUES (1, 2, 1, 0, now(), 'nice');

-- 字典表
drop table if EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`         int(10)      NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `dict_key`   varchar(255) null COMMENT 'key',
    `dict_value` varchar(255) null COMMENT 'value',
    `p_id`       int(10)      NOT NULL COMMENT '父级id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into sys_dict
values (1, 'S', '画风', 0);
insert into sys_dict
values (2, 'C', '品类', 0);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('S_1', '中国风', 1);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('S_2', '水墨', 1);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('S_3', '古风', 1);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('S_4', '日风', 1);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('S_5', '欧美风', 1);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('S_6', '韩风', 1);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('S_7', '写实风', 1);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('S_8', '萌系', 1);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('S_9', '童趣', 1);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('S_10', '自由画师', 1);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('S_11', '像素风', 1);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('C_1', '插画', 2);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('C_2', '儿插', 2);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('C_3', '手绘', 2);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('C_4', '国画', 2);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('C_5', '原画', 2);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('C_6', '水粉', 2);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('C_7', '彩铅', 2);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('C_8', '钢笔画', 2);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('C_9', '油画', 2);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('C_10', '素描画', 2);
insert into sys_dict (`dict_key`, `dict_value`, `p_id`) value ('C_11', '速写', 2);

-- ----------------------- new------------------------------------------
-- 关注人表
drop table if exists `sys_focus`;
create table `sys_focus`
(
    `id`       int(11) AUTO_INCREMENT not null comment '主键id',
    `user_id`  int(11)                not null comment '关注人id',
    `focus_id` int(11)                not null comment '被关注人id',
    primary key ('id')
) Engine = InnoDB
  DEFAULT CHARSET = utf8;



