package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.LoginRecord;
import com.example.lixc.vo.query.UserQuery;

import java.util.List;
import java.util.Map;

public interface LoginRecordMapper extends SwBaseMapper<LoginRecord> {
    List<Map<String, Integer>> selectLoginRecord(UserQuery userQuery);
}
