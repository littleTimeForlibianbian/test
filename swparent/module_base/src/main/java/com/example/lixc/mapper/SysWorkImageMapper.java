package com.example.lixc.mapper;

import com.example.lixc.entity.SysImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysWorkImageMapper {

    List<SysImage> selectImagesByWorkId(@Param("workId") int workId);
}