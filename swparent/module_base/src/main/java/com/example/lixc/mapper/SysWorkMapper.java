package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysWork;
import com.example.lixc.vo.back.WorkBack;
import com.example.lixc.vo.query.WorkQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysWorkMapper extends SwBaseMapper<SysWork> {
    //查询我的作品列表
    List<WorkBack> selectForList(@Param("workQuery") WorkQuery workQuery, @Param("more") String more);

    //查询作品详情
    WorkBack selectById(@Param("workId") int workId);
}