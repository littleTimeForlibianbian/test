package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.WFavorite;
import org.apache.ibatis.annotations.Param;

public interface WFavoriteMapper  extends SwBaseMapper<WFavorite> {
    //查询作品是否是我喜欢的作品
    int selectCountByWorkId(@Param("userId")int userId,@Param("workId")int workId);
}
