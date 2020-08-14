package com.example.lixc.controller.web.userBehavior;

import com.example.lixc.service.DataService;
import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 10:29
 */
@Api("用户行为")
@RestController
@RequestMapping("/web/manager/userBehavior")
public class UserDataController {

    private DataService dataService;

    @ApiOperation("游客访问量")
    @PostMapping("/visitors")
    public ResultJson visitors() {
        System.out.println("visitors");
        return null;
    }

    @ApiOperation("登录注册数据")
    @RequestMapping("/logonData")
    public ResultJson logonData(@RequestParam("days") int days) {
        //查询一周内  一月内  的注册用户数量  以及所再省份比例
        return dataService.getAddUser(days, false);
    }

    @ApiOperation("登录注册数据")
    @RequestMapping("/userAreaDistributed")
    public ResultJson userAreaDistributed() {
        return dataService.getUserAreaDistributed(false);
    }

    @ApiOperation("登录注册数据")
    @RequestMapping("/paintgerAreaDistributed")
    public ResultJson paintgerAreaDistributed() {
        return dataService.getUserAreaDistributed(true);
    }

    @ApiOperation("画师认证数据")
    @RequestMapping("/painter")
    public ResultJson painter(@RequestParam("days") int days) {
        //查询一周内  一月内  的注册用户数量  以及所再省份比例
        return dataService.getAddUser(days, true);
    }

    @ApiOperation("作品转发数据")
    @PostMapping("/workForwarding")
    public ResultJson workForwarding() {
        //分享
        System.out.println("workForwarding");
        return null;
    }

}
