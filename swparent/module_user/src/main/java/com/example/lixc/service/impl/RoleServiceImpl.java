package com.example.lixc.service.impl;

import com.example.lixc.entity.Role;
import com.example.lixc.entity.RolePrivilege;
import com.example.lixc.enums.BaseStatusEnum;
import com.example.lixc.mapper.PrivilegeMapper;
import com.example.lixc.mapper.RoleMapper;
import com.example.lixc.mapper.RolePrivledgeMapper;
import com.example.lixc.service.RoleService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.util.ToolsUtil;
import com.example.lixc.vo.back.RoleBack;
import com.example.lixc.vo.query.RoleQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private RoleMapper roleMapper;

    @Autowired
    private RolePrivledgeMapper rolePrivledgeMapper;

    @Autowired
    private PrivilegeMapper privilegeMapper;


    @Override
    public Page<RoleBack> selectForList(RoleQuery roleQuery) {
        log.info("param:" + roleQuery.toString());
        PageHelper.startPage(roleQuery.getPageNo(), roleQuery.getPageSize());
        List<RoleBack> roleBacks = roleMapper.selectForList(roleQuery);
        return (Page<RoleBack>) roleBacks;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultJson insert(RoleQuery roleQuery) {
        try {
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
        } catch (Exception e) {
            log.error("role  insert  exception：{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("添加角色失败");
        }
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson detail(RoleQuery roleQuery) {
        if (roleQuery.getId() <= 0) {
            return ResultJson.buildError("传入对象id为空");
        }
        Role role = roleMapper.selectByPrimaryKey(roleQuery.getId());
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
        try {
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
        } catch (Exception e) {
            log.error("role  insert  exception：{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("更新角色失败");
        }
        return null;
    }

    private void handleRolePrivilege(RoleQuery roleQuery, int role_id) {
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
        try {
            roleMapper.deleteByPrimaryKey(roleQuery.getId());
        } catch (Exception e) {
            log.error("删除角色异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("删除角色失败");
        }
        return ResultJson.buildSuccess();
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public ResultJson deleteBatch(String ids) {
//        return null;
//    }

    @Override
    public ResultJson getAllPrivileges() {
        return ResultJson.buildSuccess(privilegeMapper.selectAll());
    }


}
