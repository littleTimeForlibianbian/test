package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.Privilege;
import com.example.lixc.vo.back.PrivilegeBack;

import java.util.List;

public interface PrivilegeMapper extends SwBaseMapper<Privilege> {


    //获取所有的权限
    List<PrivilegeBack> findAllPrivilege();


}