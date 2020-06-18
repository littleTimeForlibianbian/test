-- 用户表
drop table if EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`              int(10)      NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `role_id`         int(10)      NULL COMMENT '角色id',
    `nick_name`       varchar(255) NULL COMMENT '昵称',
    `real_name`       varchar(255) null COMMENT '真实姓名',
    `location`        varchar(255) NULL COMMENT '所在地',
    `position`        varchar(255) NULL COMMENT '职位',
    `password`        varchar(255) NOT NULL COMMENT '密码',
    `email`           varchar(100) NOT NULL COMMENT '邮箱',
    `phone`           varchar(100) NOT NULL COMMENT '手机号',
    `status`          int(1)                DEFAULT -1 COMMENT '状态：1 已注册，2, 已激活 3已选择标签，4已申请画师审核，5已审核通过  6审核失败',
    `enable`          char(1)               DEFAULT 'N' COMMENT '管理员是否启用',
    `city`            varchar(100)          DEFAULT NULL COMMENT '城市',
    `administrator`   char(1)      NOT NULL DEFAULT 'N' COMMENT '是否是管理员',
    `painter`         char(1)      NULL     DEFAULT 'N' COMMENT '是否是画师',
    `invitation_code` varchar(100)          DEFAULT NULL COMMENT '邀请码',
    `create_by`       int(11)      NULL     DEFAULT '0' COMMENT '创建人',
    `create_time`     datetime     NOT NULL DEFAULT now() COMMENT '创建时间',
    `update_by`       int(11)               DEFAULT NULL COMMENT '最后更新人',
    `update_time`     datetime              DEFAULT NULL COMMENT '最后更新时间',
    `pwd_update_date` datetime              DEFAULT NULL COMMENT '密码更新日期',
    `last_login_time` datetime              DEFAULT NULL COMMENT '最后登录时间',
    `last_login_ip`   varchar(30)           DEFAULT NULL COMMENT '最后登录ip',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 初始化超级管理员
INSERT INTO `sys_user`(`id`, `role_id`, `nick_name`, `real_name`, `location`, `position`, `password`, `email`, `phone`,
                       `status`, `city`, `administrator`, `invitation_code`, `last_login_time`, `create_by`,
                       `create_time`, `update_by`, `update_time`)
VALUES (1, 1, 'admin', 'admin', 'bj', 'bj', 'password', '11@11.com', '13292692986', 4, 'bj', 'Y', NULL, NULL, 0,
        '2020-06-03 21:59:06', NULL, NULL);

-- 登录记录表
drop table if EXISTS `sys_login_record`;
CREATE TABLE `sys_login_record`
(
    `id`              int(11)      NOT NULL DEFAULT '0',
    `user_id`         int(10)      NOT NULL COMMENT '主键id',
    `user_name`       varchar(255) NOT NULL COMMENT '用户名，可以是邮箱 可以是昵称',
    `login_ip`        varchar(50)  NOT NULL COMMENT '登录 ip',
    `login_area_code` varchar(20)  NOT NULL COMMENT '登录地点(城市编码)',
    `create_time`     datetime     NOT NULL DEFAULT now() COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- 角色表
drop table if EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`               int(11)      NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `name`             varchar(255) NOT NULL DEFAULT '' COMMENT '角色名称',
    `role_description` varchar(255)          DEFAULT NULL COMMENT '角色描述',
    `enable`           char(1)               DEFAULT 'N' COMMENT '是否启用',
    `create_by`        int(11)      NULL COMMENT '创建人',
    `create_time`      datetime     NOT NULL DEFAULT now() COMMENT '创建时间',
    `update_by`        int(11)               DEFAULT NULL COMMENT '最后更新人',
    `update_time`      datetime              DEFAULT NULL COMMENT '最后更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- insert into sys_role values (1,'超级管理员','系统初始化超级管理员',0,now(),null,null);


-- 条款表
drop table if EXISTS `sys_policy`;
CREATE TABLE `sys_policy`
(
    `id`          int(10)     NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `type`        varchar(10) NOT NULL COMMENT '条款类型 use:使用条款，private:隐私条款',
    `content`     longtext    NOT NULL COMMENT '条款内容',
    `create_by`   int(11)     NOT NULL DEFAULT '0' COMMENT '创建人',
    `create_time` datetime    NOT NULL DEFAULT now() COMMENT '创建时间',
    `update_by`   int(11)              DEFAULT NULL COMMENT '最后更新人',
    `update_time` datetime             DEFAULT NULL COMMENT '最后更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 标签表
drop table if EXISTS `sys_tag`;
CREATE TABLE `sys_tag`
(
    `id`      int(10)     NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `type`    varchar(10) NOT NULL COMMENT '标签分类：1,身份;2:年代，3：画风；4：品类',
    `content` longtext    NOT NULL COMMENT '标签内容',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 邀请码表
drop table if EXISTS `sys_code`;
CREATE TABLE `sys_code`
(
    `id`          int(10)     NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `code`        varchar(20) NOT NULL COMMENT '邀请码',
    `used_num`    varchar(20) NOT NULL COMMENT '使用次数',
    `create_time` varchar(20) NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- 系统设置表
drop table if EXISTS `sys_config`;
CREATE TABLE `sys_config`
(
    `id`                     int(10)     NOT NULL COMMENT '主键id',
    `invitation_code_open`   varchar(20) NOT NULL COMMENT '是否开启邀请码',
    `invitation_code_expire` int(10)     NULL COMMENT '邀请码过期时间',
    `update_by`              int(11)  DEFAULT NULL COMMENT '最后更新人',
    `update_time`            datetime DEFAULT NULL COMMENT '最后更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
insert into sys_config
values (0, 'N', 0, null, null);


--  用户角色表
drop table if EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`      int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` int(10) NOT NULL COMMENT '用户id',
    `role_id` int(10) NOT NULL COMMENT '角色id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--  权限表
drop table if EXISTS `sys_privledge`;
CREATE TABLE `sys_privledge`
(
    `id`     int(10)      NOT NULL COMMENT '主键id',
    `name`   varchar(100) NOT NULL COMMENT '权限名称',
    `url`    varchar(100) NOT NULL COMMENT '权限路径',
    `type`   int(1)       NOT NULL default 2 COMMENT '权限类型：1前台，2后台',
    `parent` int(10)      NULL COMMENT '父级权限id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 角色权限表
drop table if EXISTS `sys_role_privledge`;
CREATE TABLE `sys_role_privledge`
(
    `id`      int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `role_id` int(10) NOT NULL COMMENT '角色id',
    `p_id`    int(10) NOT NULL COMMENT '权限id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- 用户属性表
drop table if exists `sys_user_attr`;
create table `sys_user_attr`
(
    `id`          int(11)      not null auto_increment comment '主键id',
    `user_id`     int(11)      not null comment '用户id',
    `u_history`   varchar(255) null comment '创作历史',
    `website`     varchar(255) null comment '常用网站',
    `create_time` datetime     NULL COMMENT '创建时间',
    primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


