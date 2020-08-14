package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.Tag;
import com.example.lixc.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper extends SwBaseMapper<Tag> {
    List<Tag> selectListByPainterId(@Param("userId") int userId);
}