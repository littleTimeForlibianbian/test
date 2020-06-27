package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.UserAttr;
import org.apache.ibatis.annotations.Param;

public interface UserAttrMapper extends SwBaseMapper<UserAttr> {
    UserAttr selectByUserId(@Param("userIdF")int userId);
}
