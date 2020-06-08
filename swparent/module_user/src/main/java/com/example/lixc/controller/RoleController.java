package com.example.lixc.controller;

import com.example.lixc.entity.Role;
import com.example.lixc.service.RoleService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.RoleBack;
import com.example.lixc.vo.query.RoleQuery;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description 角色管理类
 * @createTime 2020/6/7 13:08
 */
@RestController
@RequestMapping("/user/manager/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/selectForList")
    public Page<RoleBack> selectForList(RoleQuery roleQuery) {
        return roleService.selectForList(roleQuery);
    }

    @RequestMapping("/insert")
    public ResultJson insert(RoleQuery roleQuery) {
        return roleService.insert(roleQuery);
    }

    @RequestMapping("/detail")
    public ResultJson detail(RoleQuery roleQuery) {
        return roleService.detail(roleQuery);
    }

    @RequestMapping("/update")
    public ResultJson update(RoleQuery roleQuery) {
        return roleService.update(roleQuery);
    }

    @RequestMapping("/delete")
    public ResultJson delete(RoleQuery roleQuery) {
        return roleService.delete(roleQuery);
    }

//    @RequestMapping("/deleteBatch")
//    public ResultJson deleteBatch(String ids) {
//        return roleService.deleteBatch(ids);
//    }

    @RequestMapping("/getAllPrivileges")
    public ResultJson getAllPrivileges() {
        return roleService.getAllPrivileges();
    }
}
