package com.example.lixc.service.impl;

import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.entity.SysVersionSpo;
import com.example.lixc.enums.UserVoiceTypeEnum;
import com.example.lixc.enums.VersionSpoStatusEnum;
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
    @Transactional(rollbackFor = Exception.class)
    public ResultJson add(VersionSpoQuery versionSpoQuery) {
        if (StringUtils.isEmpty(versionSpoQuery.getContent())) {
            log.error("内容为空");
            return ResultJson.buildError("内容为空");
        }
        if (versionSpoQuery.getPublishTime() == null || versionSpoQuery.getPublishTime().getTime() < System.currentTimeMillis()) {
            log.error("发布时间不合法");
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
        VersionSpoBack back = mapper.detail(versionSpoQuery.getId());
        if (back.getStatus() == VersionSpoStatusEnum.SPO_STATUS_APPLY.getCode()) {
            back.setStatusCh("待优化");
        } else if (back.getStatus() == VersionSpoStatusEnum.SPO_STATUS_IN.getCode()) {
            back.setStatusCh("优化中");
        } else if (back.getStatus() == VersionSpoStatusEnum.SPO_STATUS_END.getCode()) {
            back.setStatusCh("优化完毕");
        } else {
            back.setStatusCh("未知状态");
        }
        return ResultJson.buildSuccess(back);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultJson edit(VersionSpoQuery versionSpoQuery) {
        if (versionSpoQuery.getId() < 0) {
            return ResultJson.buildError("id为空");
        }
        SysVersionSpo sysVersionSpo = mapper.selectByPrimaryKey(versionSpoQuery.getId());
        sysVersionSpo.setContent(versionSpoQuery.getContent());
        sysVersionSpo.setPublishTime(versionSpoQuery.getPublishTime());
        sysVersionSpo.setStatus(versionSpoQuery.getStatus());
        mapper.updateByPrimaryKeySelective(sysVersionSpo);
        return ResultJson.buildSuccess("编辑成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultJson deleteById(Integer id) {
        if (id == null || id <= 0) {
            return ResultJson.buildError("传入参数对象id为空");
        }
        mapper.deleteByPrimaryKey(id);
        return ResultJson.buildSuccess("删除成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
        return ResultJson.buildSuccess("批量删除成功");
    }

    @Override
    public Page<VersionSpoBack> selectForList(VersionSpoQuery versionSpoQuery) {
        PageHelper.startPage(versionSpoQuery.getPageNo(), versionSpoQuery.getPageSize());
        List<VersionSpoBack> versionSpoBacks = mapper.selectForList(versionSpoQuery);
        for (VersionSpoBack back : versionSpoBacks) {
            if (back.getStatus() == VersionSpoStatusEnum.SPO_STATUS_APPLY.getCode()) {
                back.setStatusCh("待优化");
            } else if (back.getStatus() == VersionSpoStatusEnum.SPO_STATUS_IN.getCode()) {
                back.setStatusCh("优化中");
            } else if (back.getStatus() == VersionSpoStatusEnum.SPO_STATUS_END.getCode()) {
                back.setStatusCh("优化完毕");
            } else {
                back.setStatusCh("未知状态");
            }
        }
        return (Page<VersionSpoBack>) versionSpoBacks;
    }
}
