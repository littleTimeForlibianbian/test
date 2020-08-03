package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.UFocus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 21:27
 */
public interface UFocusMapper extends SwBaseMapper<UFocus> {

    //查询是否关注过
    int selectCountById(@Param("userId") int userId, @Param("authorId") int authorId);

}
