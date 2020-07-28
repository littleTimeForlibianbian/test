package com.example.lixc.service;

import com.example.lixc.entity.SysSuggest;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.SuggestQuery;

import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/7 14:13
 */
public interface FeedbackService {
    List<SysSuggest> selectForList(SuggestQuery suggestQuery);

    ResultJson deleteById(SuggestQuery suggestQuery);

    ResultJson deleteBatch(String ids);

    ResultJson like(SuggestQuery suggestQuery);
}
