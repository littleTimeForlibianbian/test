package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysMessage;
import com.example.lixc.entity.SysUserMessage;
import com.example.lixc.vo.query.MessageQuery;

import java.util.List;

public interface SysUserMessageMapper extends SwBaseMapper<SysUserMessage> {
    //根据userId查询 消息列表
    List<SysUserMessage> selectList(MessageQuery messageQuery);

    List<SysMessage> query(MessageQuery messageQuery);

    int queryCount(MessageQuery messageQuery);
}