package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysVersionSpo;
import com.example.lixc.vo.back.VersionSpoBack;
import com.example.lixc.vo.query.VersionSpoQuery;

import java.util.List;

public interface SysVersionSpoMapper extends SwBaseMapper<SysVersionSpo> {
    void delByIds(String[] split);

    List<VersionSpoBack> selectForList(VersionSpoQuery versionSpoQuery);

}

