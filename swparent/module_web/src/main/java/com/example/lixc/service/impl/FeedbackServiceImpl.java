package com.example.lixc.service.impl;

import com.example.lixc.entity.SysSuggest;
import com.example.lixc.mapper.SysSuggestMapper;
import com.example.lixc.service.FeedbackService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.SuggestQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/7 14:21
 */
@Service
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private SysSuggestMapper suggestMapper;

    @Override
    public List<SysSuggest> selectForList(SuggestQuery suggestQuery) {
        return suggestMapper.selectForList(suggestQuery);

    }

    @Override
    public ResultJson deleteById(SuggestQuery suggestQuery) {
        if (suggestQuery.getSuggestId() <= 0) {
            return ResultJson.buildError("传入参数对象id为空");
        }
        suggestMapper.deleteByPrimaryKey(suggestQuery.getSuggestId());
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson deleteBatch(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return ResultJson.buildError("传入参数为空");
        }
        try {
            suggestMapper.delByIds(ids.split(","));
        } catch (Exception e) {
            log.error("delAdminUser exception: {}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError(e.getMessage());
        }
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson like(SuggestQuery suggestQuery) {
        if (suggestQuery.getSuggestId() <= 0) {
            return ResultJson.buildError("传入参数对象id为空");
        }
        SysSuggest suggest = suggestMapper.selectByPrimaryKey(suggestQuery.getSuggestId());
        suggest.setPraiseNum(suggest.getPraiseNum() + 1);
        suggestMapper.updateByPrimaryKeySelective(suggest);
        return ResultJson.buildSuccess();
    }
}
