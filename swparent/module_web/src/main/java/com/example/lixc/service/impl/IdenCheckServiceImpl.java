package com.example.lixc.service.impl;

import com.example.lixc.entity.User;
import com.example.lixc.enums.UserStatusEnum;
import com.example.lixc.mapper.UserMapper;
import com.example.lixc.service.IdenCheckService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.UserQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/5 14:58
 */

@Service
@Slf4j
public class IdenCheckServiceImpl implements IdenCheckService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Page<UserBack> list(UserQuery userQuery) {
        //
        PageHelper.startPage(userQuery.getPageNo(), userQuery.getPageSize());
        userQuery.setPainter("N");
        //查询全部待审核数据
        userQuery.setStatus(UserStatusEnum.USER_STATUS_STEP3.getCode());
        List<UserBack> userBacks = userMapper.selectAllUser(userQuery);
        return (Page<UserBack>) userBacks;
    }

    @Override
    public ResultJson identifyCheck(UserQuery userQuery) {
        if (userQuery.getUserId() <= 0) {
            log.error("传入参数【id】为空");
            return ResultJson.buildError("传入参数为空");
        }
        if (userQuery.getStatus() <= 0) {
            log.error("传入参数【status】为空");
            return ResultJson.buildError("传入参数为空");
        }
        Integer status = userQuery.getStatus();
        if (status != UserStatusEnum.USER_STATUS_PASS.getCode() &&
                status != UserStatusEnum.USER_STATUS_FAIL.getCode()) {
            log.error("传入参数【status】为：{},不合法", status);
            return ResultJson.buildError("传入参数不合法");
        }
        User user = userMapper.selectByPrimaryKey(userQuery.getUserId());
        if (user == null) {
            return ResultJson.buildError("查询为空");
        }
        user.setStatus(status);
        userMapper.updateByPrimaryKeySelective(user);
        return ResultJson.buildSuccess();
    }
}
