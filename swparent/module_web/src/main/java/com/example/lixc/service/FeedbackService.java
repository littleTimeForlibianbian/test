package com.example.lixc.service;

import com.example.lixc.entity.SysSuggest;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.SuggestQuery;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/7 14:13
 */
public interface FeedbackService {
    /**
     * 查询建议反馈列表集合
     *
     * @param suggestQuery 建议反馈查询对象
     * @return
     */
    Page<SysSuggest> selectForList(SuggestQuery suggestQuery);

    /**
     * 单个删除 建反馈
     *
     * @param suggestQuery 建议反馈查询对象
     * @return
     */
    ResultJson deleteById(SuggestQuery suggestQuery);

    /**
     * 批量删除建议反馈
     *
     * @param ids 待删除的id集合
     * @return
     */
    ResultJson deleteBatch(String ids);

    /**
     * 点赞建议反馈
     *
     * @param suggestQuery 建议反馈查询对象
     * @return
     */
    ResultJson like(SuggestQuery suggestQuery);
}
