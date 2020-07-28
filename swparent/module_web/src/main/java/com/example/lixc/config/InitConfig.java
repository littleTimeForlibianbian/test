package com.example.lixc.config;

import com.alibaba.fastjson.JSONObject;
import com.example.lixc.constants.RedisTimeConstant;
import com.example.lixc.dto.UserInfoDTO;
import com.example.lixc.mapper.UserMapper;
import com.example.lixc.util.RedisPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wilson
 * @Description 初始化数据加载类
 * @createTime 2020/7/16 17:00
 */
@Component
@Order(value = 1)//指定加载顺序，数组越小优先级越高
public class InitConfig implements ApplicationRunner {

    //用户信息加载缓存
    public static Map<Integer, String> userBasicMap = new HashMap<>();

    @Autowired
    private UserMapper userMapper;

    @Override
    public void run(ApplicationArguments args) {
        List<UserInfoDTO> userInfoDTOS = userMapper.selectUserInfo();
        if (!CollectionUtils.isEmpty(userInfoDTOS)) {
            for (UserInfoDTO u : userInfoDTOS) {
                userBasicMap.put(u.getUserId(), JSONObject.toJSONString(u));
            }
        }
        RedisPoolUtil.set(RedisTimeConstant.USER_INFO, userBasicMap,RedisTimeConstant.CACHE_5_MINUTE);
    }
}
