package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysDict;
import com.example.lixc.entity.SysWorkDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysWorkDictMapper extends SwBaseMapper<SysWorkDict> {
    //查询作品对应的所有的标签
    List<SysDict> selectTagsByWork(@Param("workId") int workId);

    int deleteTagsByWork(@Param("workId") int workId);
}