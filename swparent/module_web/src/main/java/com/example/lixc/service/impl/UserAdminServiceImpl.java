package com.example.lixc.service.impl;

import com.example.lixc.entity.User;
import com.example.lixc.entity.UserRole;
import com.example.lixc.mapper.UserMapper;
import com.example.lixc.mapper.UserRoleMapper;
import com.example.lixc.service.UserAdminService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.util.ToolsUtil;
import com.example.lixc.vo.back.AdminUserBack;
import com.example.lixc.vo.query.AdminUserQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/3 10:38
 */
@Service
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 获取所有的管理员 分页
     *
     * @return
     */
    public Page<AdminUserBack> selectAdminUsers(AdminUserQuery adminUserQuery) {
        log.info("param:" + adminUserQuery.toString());
        PageHelper.startPage(adminUserQuery.getPageNo(), adminUserQuery.getPageSize());
        List<AdminUserBack> adminUserBacks = userMapper.selectAllAdminUsers(adminUserQuery);
        return (Page<AdminUserBack>) adminUserBacks;
    }

    @Override
    public ResultJson addAdminUser(AdminUserQuery adminUserQuery) {
        try {
            ResultJson verifyParams = adminUserQuery.checkParams();
            if (!ToolsUtil.verifyParams(verifyParams)) {
                return verifyParams;
            }
            //校验手机号是否已经注册
            if (userMapper.existsWithPhone(adminUserQuery.getPhone(), "Y") > 0) {
                return ResultJson.buildError("手机号已经被注册");
            }
            //校验邮箱是否注册
            if (userMapper.existsWithEmail(adminUserQuery.getEmail(), "Y") > 0) {
                return ResultJson.buildError("邮箱已经被注册");
            }
            User user = new User();
            user.setAdministrator("Y");
            user.setPhone(adminUserQuery.getPhone());
            user.setEmail(adminUserQuery.getEmail());
            user.setPassword(adminUserQuery.getPassword());
            user.setEnable("Y");
            user.setRealName(adminUserQuery.getRealName());
            user.setLocation(adminUserQuery.getLocation());
            user.setPosition(adminUserQuery.getPosition());
            user.setCreateTime(new Date());
            user.setRoleId(adminUserQuery.getRoleId());
            userMapper.insertUseGeneratedKeys(user);
            //添加用户角色关联表
            UserRole userRole = new UserRole();
            userRole.setRoleId(adminUserQuery.getRoleId());
            userRole.setUserId(user.getId());
            userRoleMapper.insertSelective(userRole);
        } catch (Exception e) {
            log.error("addAdminUser exception: {}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError(e.getMessage());
        }
        return ResultJson.buildSuccess();
    }

    /**
     * 查询管理员详情
     *
     * @param adminUserQuery
     * @return
     */
    @Override
    public ResultJson detailAdminUser(AdminUserQuery adminUserQuery) {
        if (adminUserQuery.getId() <= 0) {
            return ResultJson.buildError("传入参数对象id为空");
        }
        User user = userMapper.selectByPrimaryKey(adminUserQuery.getId());
        if (user == null) {
            return ResultJson.buildError("ID为{}的对象不存在", adminUserQuery.getId());
        }
        return ResultJson.buildSuccess(user);
    }

    /**
     * 更新管理员
     *
     * @param adminUserQuery
     * @return
     */
    @Override
    public ResultJson updateAdminUser(AdminUserQuery adminUserQuery) {
        try {
            if (adminUserQuery.getId() <= 0) {
                return ResultJson.buildError("传入参数对象id为空");
            }
            User user = userMapper.selectByPrimaryKey(adminUserQuery.getId());
            if (user == null) {
                return ResultJson.buildError("ID为{}的对象不存在", adminUserQuery.getId());
            }
            user.setPhone(adminUserQuery.getPhone());
            user.setPosition(adminUserQuery.getPosition());
            user.setLocation(adminUserQuery.getLocation());
            user.setRoleId(adminUserQuery.getRoleId());
            userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            log.error("updateAdminUser exception: {}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError(e.getMessage());
        }
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson delAdminUser(AdminUserQuery adminUserQuery) {
        try {
            if (adminUserQuery.getId() <= 0) {
                return ResultJson.buildError("传入参数对象id为空");
            }
            userMapper.deleteByPrimaryKey(adminUserQuery.getId());
        } catch (Exception e) {
            log.error("delAdminUser exception: {}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError(e.getMessage());
        }
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson delAdminUserBatch(String ids) {
        try {
            userMapper.delByIds(ids.split(","));
        } catch (Exception e) {
            log.error("delAdminUser exception: {}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError(e.getMessage());
        }
        return ResultJson.buildSuccess();
    }
}
