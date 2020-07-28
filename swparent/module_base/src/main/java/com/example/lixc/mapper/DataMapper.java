package com.example.lixc.mapper;

import com.example.lixc.vo.back.UserDataBack;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/7 11:38
 */
public interface DataMapper {

    /**
     * 获取新增用户数据
     *
     * @return
     */
    List<UserDataBack> getAddUsers(@Param("start_date") String start_date,
                                   @Param("end_date") String end_date,
                                   @Param("isPainter") boolean isPainter);

    List<UserDataBack> getUserAreaDistributed(@Param("isPainter") boolean isPainter);
}
