package com.example.lixc.service.impl;

import com.example.lixc.mapper.DataMapper;
import com.example.lixc.service.DataService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.util.ToolsUtil;
import com.example.lixc.vo.back.UserDataBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/7 11:37
 */
@Service
@Slf4j
public class DataServiceImpl implements DataService {

    @Autowired
    private DataMapper dataMapper;

    @Override
    public ResultJson getAddUser(int days, boolean isPainter) {
        List<UserDataBack> list;
        TreeSet<String> daysList = ToolsUtil.getDaysList(days);
        String start_date = "";
        String end_date = "";
        Map<String, Integer> map = new TreeMap<>();
        try {
            if (daysList != null && daysList.size() > 0) {
                start_date = daysList.first();
                end_date = daysList.last();
            } else {
                throw new Exception("获取时间异常");
            }
            list = dataMapper.getAddUsers(start_date + " 00：00：00", end_date + " 23:59:59", isPainter);
            if (list != null && list.size() > 0) {
                for (UserDataBack u : list) {
                    map.put(u.getCreate_time(), u.getCount_user_add());
                }
                if (list.size() == days) {
                    return ResultJson.buildSuccess(map);
                }
                for (String day : daysList) {
                    if (!map.keySet().contains(day)) {
                        map.put(day, 0);
                    }
                }
            } else {
                for (String day : daysList) {
                    map.put(day, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultJson.buildSuccess(map);
    }

    @Override
    public ResultJson getUserAreaDistributed(boolean isPainter) {
        List<UserDataBack> userAreaDistributed = dataMapper.getUserAreaDistributed(isPainter);
        return ResultJson.buildSuccess(userAreaDistributed);
    }
}
