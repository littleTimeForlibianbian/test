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
import com.example.lixc.vo.query.UserQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
        for (AdminUserBack back : adminUserBacks) {
            if ("Y".equalsIgnoreCase(back.getEnable())) {
                back.setEnableCH("启用");
            } else {
                back.setEnableCH("停用");
            }
        }
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
            user.setNickName(adminUserQuery.getNickName());
//            user.setRoleId(adminUserQuery.getRoleId());
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
     * @param userId
     * @return
     */
    @Override
    public ResultJson detailAdminUser(Integer userId) {
        if (userId == null || userId <= 0) {
            return ResultJson.buildError("传入参数对象id为空");
        }
        AdminUserQuery userQuery = new AdminUserQuery();
        userQuery.setId(userId);
        List<AdminUserBack> adminUserBacks = userMapper.selectAllAdminUsers(userQuery);
        if (CollectionUtils.isEmpty(adminUserBacks)) {
            return ResultJson.buildError("ID为{}的对象不存在", userId);
        }
        return ResultJson.buildSuccess(adminUserBacks.get(0));
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
            user.setEnable(adminUserQuery.getEnable());
//            user.setRoleId(adminUserQuery.getRoleId());
            userMapper.updateByPrimaryKeySelective(user);
            //TODO 更新完毕之后更新 用户角色表
//            userRoleMapper.selectListByUserId()


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
            //删除用户角色的关联数据
            UserRole userRole = new UserRole();
            userRole.setUserId(adminUserQuery.getId());
            userRoleMapper.delete(userRole);
        } catch (Exception e) {
            log.error("delAdminUser exception: {}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError(e.getMessage());
        }
        return ResultJson.buildSuccess();
    }

    @Override
    @Transactional
    public ResultJson delAdminUserBatch(String ids) {
        try {
            String[] split = ids.split(",");
            userMapper.delByIds(split);
            userMapper.delUserRoleRelationByUserId(split);
        } catch (Exception e) {
            log.error("delAdminUser exception: {}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError(e.getMessage());
        }
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson updatePassword(AdminUserQuery adminUserQuery) {
        if (adminUserQuery.getId() <= 0) {
            log.error("传入参数对象id为空");
            return ResultJson.buildError("传入参数对象id为空");
        }
        User user = new User();
        user.setId(adminUserQuery.getId());
        user.setPassword(adminUserQuery.getPassword());
        user.setPwdUpdateDate(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        log.info("更新管理员密码成功");
        return ResultJson.buildSuccess("更新管理员密码成功");
    }

    @Override
    public ResultJson enable(AdminUserQuery adminUserQuery) {
        if (adminUserQuery.getId() <= 0) {
            log.error("传入参数对象id为空");
            return ResultJson.buildError("传入参数对象id为空");
        }
        if (StringUtils.isEmpty(adminUserQuery.getEnable())) {
            log.error("停用启用状态为空");
            return ResultJson.buildError("停用启用状态为空");
        }
        String message = "";
        if ("Y".equalsIgnoreCase(adminUserQuery.getEnable())) {
            message = "启用";
        } else if ("N".equalsIgnoreCase(adminUserQuery.getEnable())) {
            message = "停用";
        } else {
            log.error("停用启用状态不合法");
            return ResultJson.buildError("停用启用状态不合法");
        }
        User user = new User();
        user.setId(adminUserQuery.getId());
        user.setEnable(adminUserQuery.getEnable());
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        return ResultJson.buildSuccess(message + "管理员成功");
    }
}
