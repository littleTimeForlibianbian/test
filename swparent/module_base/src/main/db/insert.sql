insert into sys_role (name, enable, create_by)
values ('role_user', 'Y', 12);
insert into sys_privledge(id, name, url, type, parent)
VALUES (1, 'p_user', '/public/**', 2, 1)
insert into sys_role_privledge(role_id, p_id)
values (1, 1)



INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (1, '年代', 0);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (1, '90后', 1);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (1, '80后', 1);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (1, '70后', 1);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (1, '60后', 1);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (1, '50后', 1);

INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (2, '身份', 0);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (2, '插画师', 2);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (2, '漫画师', 2);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (2, '自由画师', 2);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (2, '美院学生', 2);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (2, '原画师 ', 2);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (2, '分镜师', 2);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (2, '场景师', 2);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (2, '国画师', 2);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (2, '创作新秀', 2);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (2, '资深派', 2);



INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '画风', 0);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '中国风', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '古风', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '水墨风', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '日系', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '欧美系', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '韩系', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '写实风', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '萌系', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '童趣', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '像素风', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '科幻风', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '简约风', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '唯美风', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '水彩', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '白描', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '厚涂', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '平涂', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '少女漫画', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '美式卡通', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '硬派', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '野兽派', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (3, '未来风', 3);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '品类', 0);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '角色设计', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '立绘', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '3D', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '场景', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '条漫', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '动漫', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '特效', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '分镜', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '插画', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '游戏原画', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '二次元原创', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '国画', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '版画', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '木刻', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '壁画', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '手绘', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '简笔画', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '儿童插画', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '水粉', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '彩铅', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '钢笔画', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '油画', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '速写', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '像素画', 4);
INSERT INTO `sw`.`sys_tag`(`type`, `content`, `parent_id`)
VALUES (4, '素描', 4);