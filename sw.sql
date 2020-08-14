drop table if exists `sys_message`;
create table `sys_message`
(
    id      int(11)  not null auto_increment comment '主键id',
    content longtext not null comment '消息内容',
    primary key (`id`)
) engine = InnoDB
  default charset = utf8