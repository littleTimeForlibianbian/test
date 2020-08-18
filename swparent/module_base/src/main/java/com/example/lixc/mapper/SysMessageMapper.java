package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysMessage;
import com.example.lixc.vo.back.MessageBack;
import com.example.lixc.vo.query.MessageQuery;

import java.util.List;

public interface SysMessageMapper extends SwBaseMapper<SysMessage> {


    /**
     * 查询消息列表
     *
     * @param messageQuery 消息查询对象
     * @return List<MessageBack>
     */
    List<MessageBack> selectList(MessageQuery messageQuery);

    /**
     * 查询系统消息详情
     *
     * @param id  系统消息id
     * @return
     */
    MessageBack selectDetail(Integer id);

}