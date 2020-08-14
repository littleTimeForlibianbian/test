package com.example.lixc.service.impl;

import com.example.lixc.entity.Role;
import com.example.lixc.entity.RolePrivilege;
import com.example.lixc.enums.BaseStatusEnum;
import com.example.lixc.mapper.*;
import com.example.lixc.service.RoleService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.util.ToolsUtil;
import com.example.lixc.vo.back.RoleBack;
import com.example.lixc.vo.query.RoleQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/7 23:11
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePrivledgeMapper rolePrivledgeMapper;

    @Autowired
    private PrivilegeMapper privilegeMapper;


    @Override
    public Page<RoleBack> selectForList(RoleQuery roleQuery) {
        log.info("param:" + roleQuery.toString());
        List<RoleBack> roleBacks = null;
        try {
            PageHelper.startPage(roleQuery.getPageNo(), roleQuery.getPageSize());
            roleBacks = roleMapper.selectForList(roleQuery);
        } catch (Exception e) {
            log.error("查询角色列表异常:{}", e.getMessage());
        }
        return (Page<RoleBack>) roleBacks;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultJson insert(RoleQuery roleQuery) {
        ResultJson verifyParams = roleQuery.checkParams();
        if (!ToolsUtil.verifyParams(verifyParams)) {
            return verifyParams;
        }
        Role role = new Role();
        role.setCreateTime(new Date());
        role.setEnable(BaseStatusEnum.STATUS_ENABLE.getCode());
        role.setName(roleQuery.getName());
        role.setRoleDescription(roleQuery.getRoleDescription());
        roleMapper.insertUseGeneratedKeys(role);
        handleRolePrivilege(roleQuery, role.getId());
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson detail(RoleQuery roleQuery) {
        if (roleQuery.getId() <= 0) {
            return ResultJson.buildError("传入对象id为空");
        }
        RoleBack role = roleMapper.selectRoleDetail(roleQuery);
        return ResultJson.buildSuccess(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultJson update(RoleQuery roleQuery) {
        if (roleQuery.getId() <= 0) {
            return ResultJson.buildError("传入对象id为空");
        }
        ResultJson verifyParams = roleQuery.checkParams();
        if (!ToolsUtil.verifyParams(verifyParams)) {
            return verifyParams;
        }
        Role role = roleMapper.selectByPrimaryKey(roleQuery.getId());
        if (role == null) {
            return ResultJson.buildError("根据id查询到对象为空");
        }
        role.setRoleDescription(roleQuery.getRoleDescription());
        role.setName(roleQuery.getName());
        role.setUpdateTime(new Date());
        roleMapper.updateByPrimaryKeySelective(role);
        rolePrivledgeMapper.deleteByRoleId(roleQuery.getId());
        handleRolePrivilege(roleQuery, role.getId());
        return ResultJson.buildSuccess();
    }

    @Transactional(rollbackFor = Exception.class)
    public void handleRolePrivilege(RoleQuery roleQuery, int role_id) {
        String privilegeIds = roleQuery.getPrivilegeIds();
        String[] split = privilegeIds.split(",");
        List<RolePrivilege> rolePrivilegeList = new ArrayList<>();
        for (String pid : split) {
            RolePrivilege rolePrivilege = new RolePrivilege();
            rolePrivilege.setRoleId(role_id);
            rolePrivilege.setPId(Integer.valueOf(pid));
            rolePrivilegeList.add(rolePrivilege);
        }
        rolePrivledgeMapper.insertList(rolePrivilegeList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultJson delete(RoleQuery roleQuery) {
        if (roleQuery.getId() <= 0) {
            return ResultJson.buildError("传入对象id为空");
        }
        //删除角色
        roleMapper.deleteByPrimaryKey(roleQuery.getId());
        //查询角色是否正在被使用
        if (userMapper.selectCountByRoleId(roleQuery.getId()) > 0) {
            return ResultJson.buildError("该角色正在被使用，不能删除");
        }
        //删除角色关联
        RolePrivilege rolePrivilege = new RolePrivilege();
        rolePrivilege.setRoleId(roleQuery.getId());
        rolePrivledgeMapper.delete(rolePrivilege);
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson getAllPrivileges() {
        return ResultJson.buildSuccess(privilegeMapper.selectAll());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultJson enable(RoleQuery roleQuery) {
        if (roleQuery.getId() <= 0) {
            log.error("传入对象id为空");
            return ResultJson.buildError("传入对象id为空");
        }
        if (StringUtils.isEmpty(roleQuery.getEnable())) {
            log.error("状态值为空");
            return ResultJson.buildError("状态值为空");
        }
        String message = "";
        if ("Y".equalsIgnoreCase(roleQuery.getEnable())) {
            message = "启用";
        } else {
            message = "停用";
        }
        Role role = new Role();
        role.setId(roleQuery.getId());
        role.setEnable(roleQuery.getEnable());
        roleMapper.updateByPrimaryKeySelective(role);
        log.info(message + "角色成功");
        return ResultJson.buildSuccess(message + "角色成功");
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultJson deleteBatch(String ids) {
        if (StringUtils.isEmpty(ids)) {
            log.error("传入对象id为空");
            return ResultJson.buildError("传入对象id为空");
        }

        String[] split = ids.split(",");
        roleMapper.deleteBatch(split);
        //批量删除角色对应的权限
        rolePrivledgeMapper.deleteByRoleIdBatch(split);
        log.info("批量删除角色成功：{}", Arrays.toString(split));
        return ResultJson.buildSuccess("批量删除角色成功");
    }
}
