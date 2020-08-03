package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysComment;
import com.example.lixc.vo.back.CommentBack;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysCommentMapper extends SwBaseMapper<SysComment> {

    List<CommentBack> selectCommentList(SysComment comment);

    List<Integer> selectByParentId(@Param("parentId") int parentId);

    int deleteByBatch(@Param("ids") List<Integer> ids);
}