package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysDict;

import java.util.List;

public interface SysDictMapper extends SwBaseMapper<SysDict> {

    /**
     * 根据查询参数 模糊查询作品标签对应的id
     * @param param
     * @return
     */
    List<Integer> selectIdsByParam(String param);
}