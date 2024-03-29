package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.LoginRecord;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.back.UserLoginBack;
import com.example.lixc.vo.query.UserQuery;

import java.util.List;
import java.util.Map;

public interface LoginRecordMapper extends SwBaseMapper<LoginRecord> {
    List<UserBack> selectLoginRecord(UserQuery userQuery);
    //查询是否有用户的登录记录
    int  selectLoginRecordCount(UserQuery userQuery);
}
