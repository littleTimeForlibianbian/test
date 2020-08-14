package com.example.lixc.config;

import com.alibaba.fastjson.JSONObject;
import com.example.lixc.constants.RedisTimeConstant;
import com.example.lixc.dto.UserInfoDTO;
import com.example.lixc.mapper.UserMapper;
import com.example.lixc.util.RedisPoolUtil;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.UserQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wilson
 * @Description 初始化数据加载类
 * @createTime 2020/7/16 17:00
 */
@Slf4j
@Component
@Order(value = 1)//指定加载顺序，数组越小优先级越高
public class InitConfig implements ApplicationRunner {

    //用户信息加载缓存
    //用户信息变更或者新增时，更新此缓存内容
    public static Map<Integer, String> userBasicMap = new HashMap<>();
    //用户list，单点使用
    public static List<UserBack> userBackList;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void run(ApplicationArguments args) {
        userBackList = userMapper.selectAllUser(new UserQuery());
        if (!CollectionUtils.isEmpty(userBackList)) {
            for (UserBack u : userBackList) {
                userBasicMap.put(u.getId(), JSONObject.toJSONString(u));
            }
        }
        RedisPoolUtil.set(RedisTimeConstant.USER_INFO, userBasicMap);
    }

    public static String getNickName(Integer userId) {
        String result = null;
        if (userId == null || userId <= 0) {
            log.info("result:{}", result);
            return result;
        }
        String jsonString = userBasicMap.get(userId);
        try {
            UserBack userInfoDTO = JSONObject.parseObject(jsonString, UserBack.class);
            result = userInfoDTO.getNickName();
        } catch (Exception e) {
            log.error("转换用户数据异常:{}" + e.getMessage());
        }
        return result;
    }

}
