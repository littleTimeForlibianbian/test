package com.example.lixc.controller.web.adminManage;

import com.example.lixc.service.RoleService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.RoleBack;
import com.example.lixc.vo.query.RoleQuery;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description 角色管理类
 * @createTime 2020/6/7 13:08
 */
@Slf4j
@Api("角色管理类")
@RestController
@RequestMapping("/web/manager/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("查询角色列表")
    @PostMapping("/selectForList")
    public Page<RoleBack> selectForList(RoleQuery roleQuery) {
        return roleService.selectForList(roleQuery);
    }

    @ApiOperation("添加角色")
    @PostMapping("/insert")
    public ResultJson insert(RoleQuery roleQuery) {
        try {
            return roleService.insert(roleQuery);
        } catch (Exception e) {
            log.error("role  insert  exception：{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("添加角色失败");
        }
    }

    @ApiOperation("角色详情")
    @PostMapping("/detail")
    public ResultJson detail(RoleQuery roleQuery) {
        return roleService.detail(roleQuery);
    }

    @ApiOperation("更新角色")
    @PostMapping("/update")
    public ResultJson update(RoleQuery roleQuery) {
        try {
            return roleService.update(roleQuery);
        } catch (Exception e) {
            log.error("role  insert  exception：{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("添加角色失败");
        }
    }

    @ApiOperation("删除角色")
    @PostMapping("/delete")
    public ResultJson delete(RoleQuery roleQuery) {
        try {
            return roleService.delete(roleQuery);
        } catch (Exception e) {
            log.error("role  insert  exception：{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("添加角色失败");
        }
    }

    //   @RequestMapping("/deleteBatch")
//    public ResultJson deleteBatch(String ids) {
//        return roleService.deleteBatch(ids);
//    }
    @ApiOperation("查询所有权限")
    @PostMapping("/getAllPrivileges")
    public ResultJson getAllPrivileges() {
        return roleService.getAllPrivileges();
    }
}
