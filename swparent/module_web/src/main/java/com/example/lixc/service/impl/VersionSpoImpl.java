package com.example.lixc.service.impl;

import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.entity.SysVersionSpo;
import com.example.lixc.enums.UserVoiceTypeEnum;
import com.example.lixc.mapper.SysVersionSpoMapper;
import com.example.lixc.service.VersionSpoService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.VersionSpoBack;
import com.example.lixc.vo.query.VersionSpoQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/7 22:41
 */
@Service
@Slf4j
public class VersionSpoImpl implements VersionSpoService {

    @Autowired
    private SysVersionSpoMapper mapper;

    @Override
    @Transactional
    public ResultJson add(VersionSpoQuery versionSpoQuery) {
        if (StringUtils.isEmpty(versionSpoQuery.getContent())) {
            return ResultJson.buildError("内容为空");
        }
        if (versionSpoQuery.getPublishTime() == null || versionSpoQuery.getPublishTime().getTime() < new Date().getTime()) {
            return ResultJson.buildError("发布时间不合法");
        }
        SysVersionSpo versionSpo = new SysVersionSpo();
        int userId = SysConfigUtil.getLoginUserId();
        versionSpo.setUserId(userId);
        versionSpo.setCreateTime(new Date());
        versionSpo.setVersion(SysConfigUtil.selectCurrentVersion());
        versionSpo.setContent(versionSpoQuery.getContent());
        versionSpo.setPublishTime(versionSpoQuery.getPublishTime());

        if (UserVoiceTypeEnum.TYPE_VERSIONSPO.getCode().equalsIgnoreCase(versionSpoQuery.getType())) {
            versionSpo.setType(UserVoiceTypeEnum.TYPE_VERSIONSPO.getCode());
        } else {
            versionSpo.setType(UserVoiceTypeEnum.TYPE_FEEDBACKOPT.getCode());
            versionSpo.setStatus(versionSpoQuery.getStatus());
        }
        mapper.insertSelective(versionSpo);
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson detail(VersionSpoQuery versionSpoQuery) {
        if (versionSpoQuery.getId() < 0) {
            return ResultJson.buildError("id为空");
        }
        SysVersionSpo sysVersionSpo = mapper.selectByPrimaryKey(versionSpoQuery.getId());
        return ResultJson.buildSuccess(sysVersionSpo);
    }

    @Override
    @Transactional
    public ResultJson edit(VersionSpoQuery versionSpoQuery) {
        if (versionSpoQuery.getId() < 0) {
            return ResultJson.buildError("id为空");
        }
        SysVersionSpo sysVersionSpo = mapper.selectByPrimaryKey(versionSpoQuery.getId());
        sysVersionSpo.setContent(versionSpoQuery.getContent());
        sysVersionSpo.setPublishTime(versionSpoQuery.getPublishTime());
        mapper.updateByPrimaryKeySelective(sysVersionSpo);
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson deleteById(String id) {
        if (StringUtils.isEmpty(id)) {
            return ResultJson.buildError("传入参数对象id为空");
        }
        mapper.deleteByPrimaryKey(id);
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson deleteByBatch(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return ResultJson.buildError("传入参数为空");
        }
        try {
            mapper.delByIds(ids.split(","));
        } catch (Exception e) {
            log.error("deleteByBatch exception: {}", e.getMessage());
            return ResultJson.buildError(e.getMessage());
        }
        return ResultJson.buildSuccess();
    }

    @Override
    public Page<VersionSpoBack> selectForList(VersionSpoQuery versionSpoQuery) {
        PageHelper.startPage(versionSpoQuery.getPageNo(), versionSpoQuery.getPageSize());
        List<VersionSpoBack> versionSpoBacks = mapper.selectForList(versionSpoQuery);
        return (Page<VersionSpoBack>) versionSpoBacks;
    }
}
