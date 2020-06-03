package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.Code;

public interface CodeMapper extends SwBaseMapper<Code> {
    int selectCountByCode(String code);
}