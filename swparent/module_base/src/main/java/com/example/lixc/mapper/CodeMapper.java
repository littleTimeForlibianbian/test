package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.Code;
import org.apache.ibatis.annotations.Param;

public interface CodeMapper extends SwBaseMapper<Code> {
    int selectCountByCode(@Param("code") String code);

    int updateCount(Code code);
}