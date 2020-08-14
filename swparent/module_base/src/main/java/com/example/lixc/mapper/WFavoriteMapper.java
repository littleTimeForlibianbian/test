package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysWork;
import com.example.lixc.entity.WFavorite;
import com.example.lixc.vo.back.WorkBack;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WFavoriteMapper extends SwBaseMapper<WFavorite> {
    //查询我喜欢作品数量
    int selectCountByWorkId(@Param("userId") int userId, @Param("workId") int workId);

    int deleteByWorkId(String type, @Param("workId") int workId);

    //查询我喜欢的全部的作品
    List<WorkBack> selectMyFavoriteWork(@Param("userId") int userId);
}
