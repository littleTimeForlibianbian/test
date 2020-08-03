package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysMessage;
import com.example.lixc.entity.SysUserMessage;
import com.example.lixc.vo.query.MessageQuery;
import com.example.lixc.vo.query.UserMessageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMessageMapper extends SwBaseMapper<SysUserMessage> {
    //根据userId查询 消息列表
    List<SysUserMessage> selectList(@Param("userId") Integer userId,
                                    @Param("isRead") String isRead);

    List<SysMessage> query(UserMessageQuery userMessageQuery);

    //TODO 只用来查询我的世界页面的列表的展示，需要加分页
    List<SysMessage> queryHomeMessage(@Param("userId") Integer userId);

    int queryCount(UserMessageQuery userMessageQuery);
}