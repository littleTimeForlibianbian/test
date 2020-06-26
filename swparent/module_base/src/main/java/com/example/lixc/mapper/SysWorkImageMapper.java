package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysImage;
import com.example.lixc.entity.SysWorkImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysWorkImageMapper extends SwBaseMapper<SysWorkImage> {

    List<SysImage> selectImagesByWorkId(@Param("workId") int workId);
}