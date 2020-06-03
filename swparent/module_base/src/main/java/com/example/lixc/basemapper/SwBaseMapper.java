package com.example.lixc.basemapper;


import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface SwBaseMapper<T> extends BaseMapper<T>, MySqlMapper<T> {

}
