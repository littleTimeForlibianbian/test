package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysSuggest;
import com.example.lixc.vo.query.SuggestQuery;

import java.util.List;

public interface SysSuggestMapper extends SwBaseMapper<SysSuggest> {

    List<SysSuggest> selectForList(SuggestQuery suggestQuery);


    void delByIds(String[] ids);
}