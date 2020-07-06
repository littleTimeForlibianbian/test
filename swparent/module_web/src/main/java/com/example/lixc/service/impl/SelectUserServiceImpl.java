package com.example.lixc.service.impl;

import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.entity.SysConfig;
import com.example.lixc.entity.Tag;
import com.example.lixc.entity.User;
import com.example.lixc.mapper.LoginRecordMapper;
import com.example.lixc.mapper.UserMapper;
import com.example.lixc.service.SelectUserService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.UserQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/3 10:42
 */
@Service
@Slf4j
public class SelectUserServiceImpl implements SelectUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Override
    public Page<UserBack> selectForList(UserQuery userQuery) {
        PageHelper.startPage(userQuery.getPageNo(), userQuery.getPageSize());
        List<UserBack> userBacks = userMapper.selectAllUser(userQuery);
        return (Page<UserBack>) userBacks;
    }

    @Override
    public ResultJson enableUser(UserQuery userQuery) {
        if (userQuery.getUserId() < 0) {
            return ResultJson.buildError("传入参数错误");
        }
        Integer enable = userQuery.getEnable();
        if (enable == null) {
            return ResultJson.buildError("状态为空");
        }
        String message = enable == 0 ? "停用" : "启用";
        User user = userMapper.selectByPrimaryKey(userQuery.getUserId());
        if (user == null) {
            return ResultJson.buildError("对象不存在");
        }
        user.setEnable(enable);
        userMapper.updateByPrimaryKeySelective(user);
        return ResultJson.buildSuccess(message + "成功");
    }

    @Override
    public ResultJson painterDetail(UserQuery userQuery) {
        if (userQuery.getUserId() < 0) {
            return ResultJson.buildError("传入参数错误");
        }
        List<UserBack> userList = userMapper.selectAllUser(userQuery);
        if (CollectionUtils.isEmpty(userList)) {
            return ResultJson.buildError("对象不存在");
        }
        UserBack userBack = userList.get(0);
        if ("N".equalsIgnoreCase(userBack.getPainter())) {
            log.error("用户id;{},尚未认证画师", userQuery.getUserId());
            return ResultJson.buildError("此用户尚未认证画师");
        }
        //获取所有的标签
        List<Tag> tagList = userBack.getTagList();
        return ResultJson.buildSuccess(tagList);
    }

    @Override
    //查询活跃用户
    public Page<UserBack> selectForActiveList(UserQuery userQuery) {
        List<Map<String, Integer>> resultMap = loginRecordMapper.selectLoginRecord(userQuery);
        List<UserBack> userBackList = new ArrayList<>();
        for (Map<String, Integer> map : resultMap) {
            if (map.get("COUNT") >= SysConfigUtil.selectSysConfig().getActiveNum()) {
                Integer user_id = map.get("USER_ID");
                UserQuery user = new UserQuery();
                user.setUserId(user_id);
                UserBack userBack = userMapper.selectByUserName(user);
                userBackList.add(userBack);
            }
        }
        return (Page<UserBack>) userBackList;
    }
}
