package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysSuggest;
import com.example.lixc.vo.query.SuggestQuery;

import java.util.List;

public interface SysSuggestMapper extends SwBaseMapper<SysSuggest> {

    /**
     * 查询建议反馈列表
     *
     * @param suggestQuery 建议反馈相关接口请求参数封装类
     * @return
     */
    List<SysSuggest> selectForList(SuggestQuery suggestQuery);


    /**
     * 批量删除建议反馈
     *
     * @param ids id集合
     */
    void delByIds(String[] ids);
}