insert into  sys_role (name,enable,create_by)values ('role_user','Y',12);
insert into sys_privledge(id, name, url, type, parent) VALUES (1,'p_user','/public/**',2,1)
insert into sys_role_privledge(role_id, p_id) values (1,1)