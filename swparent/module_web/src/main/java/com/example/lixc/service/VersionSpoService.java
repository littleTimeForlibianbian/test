package com.example.lixc.service;

import com.example.lixc.entity.SysVersionSpo;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.VersionSpoBack;
import com.example.lixc.vo.query.VersionSpoQuery;
import com.github.pagehelper.Page;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/7 22:37
 */
public interface VersionSpoService {

    ResultJson add(VersionSpoQuery sysVersionSpo);

    ResultJson detail(VersionSpoQuery sysVersionSpo);

    ResultJson edit(VersionSpoQuery sysVersionSpo);

    ResultJson deleteById(String id);

    ResultJson deleteByBatch(String ids);

    Page<VersionSpoBack> selectForList(VersionSpoQuery sysVersionSpo);
}
