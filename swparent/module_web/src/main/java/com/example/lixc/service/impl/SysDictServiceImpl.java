package com.example.lixc.service.impl;

import com.example.lixc.entity.SysDict;
import com.example.lixc.mapper.SysDictMapper;
import com.example.lixc.service.SysDictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: SysDictServiceImpl
 * @description: TODO
 * @Author: Wilson
 * @createTime 2020/7/23 15:47
 */
@Service
@Slf4j
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictMapper dictMapper;

    @Override
    public List<SysDict> getDict(String type) {
        SysDict dict = new SysDict();
        if (!StringUtils.isEmpty(type)) {
            if ("category".equalsIgnoreCase(type)) {
                dict.setPId(2);
            } else {
                dict.setPId(1);
            }
        }
        return dictMapper.select(dict);
    }
}
