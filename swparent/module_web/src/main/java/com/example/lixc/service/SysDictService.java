package com.example.lixc.service;

import com.example.lixc.entity.SysDict;

import java.util.List;

/**
 * @className: SysDictService
 * @description: 作品标签service
 * @Author: Wilson
 * @createTime 2020/7/23 15:45
 */
public interface SysDictService {


    /**
     * 获取作品标签集合
     *
     * @return
     */
    List<SysDict> getDict(String type);

}
