package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysVersionSpo;
import com.example.lixc.vo.back.VersionSpoBack;
import com.example.lixc.vo.query.VersionSpoQuery;

import java.util.List;

/**
 * @author 11930
 */
public interface SysVersionSpoMapper extends SwBaseMapper<SysVersionSpo> {
    /**
     * 批量删除
     *
     * @param split
     */
    void delByIds(String[] split);

    /**
     * 查询反馈优化/版本剧透 列表
     *
     * @param versionSpoQuery
     * @return
     */
    List<VersionSpoBack> selectForList(VersionSpoQuery versionSpoQuery);


    /**
     * 查询详情
     * @param id 主键id
     * @return
     */
    VersionSpoBack detail(Integer id);

}

