package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysUserTag;
import com.example.lixc.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserTagMapper extends SwBaseMapper<SysUserTag> {
    List<Tag> selectTagsByUserId(@Param("userId") int userId);
}