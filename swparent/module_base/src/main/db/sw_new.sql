-- 用户标签表 用来存储用户选择的标签
drop table if exists `sys_user_tag`;
create table `sys_user_tag`
(
    `id`      int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` int(10) NULL COMMENT '主键id',
    `tag_id`  int(10) NULL COMMENT '主键id',
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
    `is_normal`   char(1)      not null default 'Y' comment '是否是普通作品上传',
    `name`        varchar(255) null comment '作品名称',
    `content`     varchar(255) null comment '作品内容/作品描述',
#     `work_style`    varchar(255) not null comment '画风',
#     `work_category` varchar(255) not null comment '品类',
    `praise_num`  int(11)      not NULL DEFAULT 0 COMMENT '点赞数',
    `comment_num` int(11)      not NULL DEFAULT 0 COMMENT '评论数',
    `create_by`   int(11) COMMENT '创建人',
    `create_time` datetime     NULL     DEFAULT now() COMMENT '创建时间',
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
    `thumb_url`   varchar(255)  null comment '缩略图存储路径',
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
    `id`          int(10)     NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id`     int(10)     NOT NULL COMMENT '用户id',
    `target_id`   int(10)     NOT NULL COMMENT '目标id作品id/评论id',
    `type`        varchar(10) null comment '点赞的类型，作品为work，评论为comment',
    `create_time` datetime    NOT NULL DEFAULT now() COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--  我的关注表
drop table if exists `u_focus`;
create table `u_focus`
(
    `id`          int(10)  NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id`     int(10)  NULL COMMENT '用户id',
    `author_id`   int(10)  NULL COMMENT '被关注者id',
    `is_cancel`   char(1)  NULL COMMENT '是否取消关注',
    `create_time` datetime NULL DEFAULT now() COMMENT '创建时间',
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
    `comment_level` tinyint(4)   NULL DEFAULT '1' COMMENT '评论等级[ 1 一级评论 默认 ，2 二级评论]',
    `parent_id`     int(11)      NULL COMMENT '父级id',
    `top_status`    tinyint(4)   NULL DEFAULT 0 COMMENT '置顶状态[ 1 置顶，0 不置顶 默认 ]',
    `praise_num`    int(11)      NULL DEFAULT '0' COMMENT '点赞数',
    `create_time`   datetime     NULL DEFAULT now() COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
-- 回复表  暂时不用
drop table if exists `w_reply`;
create table `w_reply`
(
    `id`         int(11)       NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `comment_id` int(11)       not null comment '评论id',
    `reply_id`   int(11)       not null comment '回复id，当回复为评论时此处为评论id',
    `content`    varchar(2000) null comment '回复内容',
    `from_uid`   int(11)       null comment '回复用户id',
    `to_uid`     int(11)       null comment '目标用户id',
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
-- 消息内容表
drop table if exists `sys_message`;
create table `sys_message`
(
    `id`           int(11)      not null auto_increment comment '主键id',
    `content`      longtext     not null comment '消息内容',
    `type`         int(10)      null comment '消息类型:announcement公告/remind提醒/message私信',
    `action`       varchar(10)  null comment '用户动作触发的消息 comment评论，praise点赞，reply回复，recommend推荐',
    `title`        varchar(255) null comment '消息标题',
    `source_id`    int(11)      null comment '来源id  作品id或者评论id',
    `source_type`  varchar(10)  null comment '来源类型  work 作品  ，comment 评论',
    --  为什么要在消息表中增加  发送者和接受者呢？
    `from_user_id` int(11) comment '发送者id  当前用户id',
    `to_user_id`   int(11) comment '接受者id source_id 所属的作者id',
    `create_time`  datetime     NULL DEFAULT now() COMMENT '创建时间',
    `send_time`    datetime     NULL DEFAULT now() COMMENT '发送时间',
    `is_delete`    char(1)      NULL DEFAULT 'N' COMMENT '是否删除',
    primary key (`id`)
) engine = InnoDB
  default charset = utf8;

-- 消息发送记录表  查询我的未读的消息，从这个表中查
drop table if exists `sys_user_message`;
create table `sys_user_message`
(
    `id`           int(11)  not null auto_increment comment '主键id',
    `message_id`   int(11)  not null comment '消息id',
    `send_time`    datetime NULL DEFAULT now() COMMENT '发送时间/创建时间',
    `from_user_id` int(11) comment '发送者id',
    `to_user_id`   int(11) comment '接受者id',
    `is_read`      char(1)  null default 'N' comment '是否已读',
    primary key (`id`)
) engine = InnoDB
  default charset = utf8;

-- 建议反馈表
drop table if exists `sys_suggest`;
create table `sys_suggest`
(
    `id`          int(11) not null auto_increment comment '主键id',
    `user_id`     int(11) comment '用户id',
    `content`     varchar(2000) comment '建议内容',
    `create_time` datetime comment '创建时间',
    `version`     int(5) comment '当前版本',
    `url`         varchar(255) comment '用户头像路径',
    `praise_num`  int(5) comment '点赞数量',
    `priority`    int(5) comment '优先级',
    `status`      int(5) comment '状态',
    primary key (`id`)
) engine = InnoDB
  default charset = utf8;

-- TODO  增加作品转发记录表  用来统计


drop table if exists `sys_version_spo`;
create table `sys_version_spo`
(
    `id`           int(11) not null auto_increment comment '主键id',
    `user_id`      int(11) comment '用户id',
    `status`       int(11) default -1 comment '状态',
    `content`      varchar(2000) comment '建议内容',
    `publish_time` datetime comment '发布时间',
    `create_time`  datetime comment '创建时间',
    `type`         varchar(10) comment '类型  版本剧透spo  反馈优化opt',
    `version`      int(5) comment '当前版本',
    primary key (`id`)
) engine = InnoDB
  default charset = utf8;


drop table if exists `sys_report`;
create table `sys_report`
(
    `id`           int(11) not null auto_increment comment '主键id',
    `content`      varchar(255) comment '举报条件',
    `content_ext1` varchar(255) comment '附加条件1',
    `content_ext2` varchar(255) comment '附加条件2',
    `create_time`  datetime comment '创建时间',
    primary key (`id`)
) engine = InnoDB
  default charset = utf8;


drop table if exists `sys_report_record`;
create table `sys_report_record`
(
    `id`          int(11) not null auto_increment comment '主键id',
    `report_id`   int(11) not null comment '举报id',
    `user_id`     int(11) not null comment '举报人id',
    `create_time` datetime comment '创建时间',
    primary key (`id`)
) engine = InnoDB
  default charset = utf8;

# 协议表
