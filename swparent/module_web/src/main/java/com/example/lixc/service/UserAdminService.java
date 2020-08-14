package com.example.lixc.service;

import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.AdminUserBack;
import com.example.lixc.vo.query.AdminUserQuery;
import com.github.pagehelper.Page;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/3 10:37
 */
public interface UserAdminService {
    /**
     * 获取所有的管理员用户
     *
     * @param adminUserQuery
     * @return
     */
    Page<AdminUserBack> selectAdminUsers(AdminUserQuery adminUserQuery);


    /**
     * 添加管理员
     *
     * @param adminUserQuery
     * @return
     */
    ResultJson addAdminUser(AdminUserQuery adminUserQuery);

    /**
     * 查询管理员详情
     *
     * @param userId
     * @return
     */
    ResultJson detailAdminUser(Integer userId);

    /**
     * 更新管理员  用户名不能更新
     *
     * @param adminUserQuery
     * @return
     */
    ResultJson updateAdminUser(AdminUserQuery adminUserQuery);

    /**
     * 单个删除管理员
     *
     * @param adminUserQuery
     * @return
     */
    ResultJson delAdminUser(AdminUserQuery adminUserQuery);

    /**
     * 批量删除管理员
     *
     * @param ids
     * @return
     */
    ResultJson delAdminUserBatch(String ids);

    ResultJson updatePassword(AdminUserQuery adminUserQuery);

    ResultJson enable(AdminUserQuery adminUserQuery);
}
