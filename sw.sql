-- 用户表
drop table if EXISTS  `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(10) NOT NULL COMMENT '主键id',
  `role_id` int(10) NOT NULL COMMENT '角色id',
  `nick_name` varchar(255) NOT NULL COMMENT '昵称',
  `real_name` varchar(255) NOT NULL COMMENT '真实姓名',
  `location` varchar(255) NOT NULL COMMENT '所在地',
  `position` varchar(255) NOT NULL COMMENT '职位',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` int(1) DEFAULT NULL COMMENT '状态：1 已注册，2已选择标签，3已认证，4已审核通过',
  `city` varchar(100) DEFAULT NULL COMMENT '城市',
  `administrator` char(1) NOT NULL DEFAULT 'N' COMMENT '是否是超级管理员',
  `invitation_code` varchar(100) DEFAULT NULL COMMENT '邀请码',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `create_by` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '最后更新人',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 初始化超级管理员
INSERT INTO `sw`.`sys_user`(`id`, `role_id`, `nick_name`, `real_name`, `location`, `position`, `password`, `email`, `phone`, `status`, `city`, `administrator`, `invitation_code`, `last_login_time`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (1, 1, 'admin', 'admin', 'bj', 'bj', 'password', '11@11.com', '13292692986', 4, 'bj', 'Y', NULL, NULL, 0, '2020-06-03 21:59:06', NULL, NULL);

-- 登录记录表
drop table if EXISTS  `sys_login_record`;
CREATE TABLE `sys_login_record` (
  `id` int(11) NOT NULL DEFAULT '0',
  `user_id` int(10) NOT NULL COMMENT '主键id',
  `nick_name`  varchar(255) NOT NULL COMMENT '昵称',
  `login_ip`  varchar(50) NOT NULL COMMENT '登录 ip',
  `login_area_code`  varchar(20) NOT NULL COMMENT '登录地点(城市编码)',
  `create_time` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 角色表
drop table if EXISTS  `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `create_by` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '最后更新人',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into sys_role values (1,'超级管理员','系统初始化超级管理员',0,now(),null,null);


-- 条款表
drop table if EXISTS  `sys_policy`;
CREATE TABLE `sys_policy` (
  `id` int(10) NOT NULL COMMENT '主键id',
  `type` varchar(10) NOT NULL COMMENT '条款类型 use:使用条款，private:隐私条款',
  `content` longtext NOT NULL COMMENT '条款内容',
  `create_by` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '最后更新人',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 标签表
drop table if EXISTS  `sys_tag`;
CREATE TABLE `sys_tag` (
  `id` int(10) NOT NULL COMMENT '主键id',
  `type` varchar(10) NOT NULL COMMENT '标签分类：1,身份;2:年代，3：画风；4：品类',
  `content` longtext NOT NULL COMMENT '条款内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 邀请码表
drop table if EXISTS  `sys_code`;
CREATE TABLE `sys_code` (
  `id` int(10) NOT NULL COMMENT '主键id',
  `code` varchar(20) NOT NULL COMMENT '邀请码',
  `used_num` varchar(20) NOT NULL COMMENT '使用次数',
  `create_time` varchar(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 系统设置表
drop table if EXISTS  `sys_config`;
CREATE TABLE `sys_config` (
  `id` int(10) NOT NULL COMMENT '主键id',
  `invitation_code_open` varchar(20) NOT NULL COMMENT '是否开启邀请码',
  `invitation_code_expire` int(10)  NULL COMMENT '邀请码过期时间',
  `update_by` int(11) DEFAULT NULL COMMENT '最后更新人',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into sys_config values (0,'N',0,null,null);

