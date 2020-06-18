package com.example.lixc.mapper;

import com.example.lixc.entity.SysWork;

public interface SysWorkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysWork record);

    int insertSelective(SysWork record);

    SysWork selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysWork record);

    int updateByPrimaryKey(SysWork record);
}