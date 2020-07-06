package com.example.lixc.controller.web.userBehavior;

import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 10:29
 */
@Api("用户行为")
@RestController
@RequestMapping("/web/manager/user/data")
public class UserDataController {

    @ApiOperation("游客访问量")
    @PostMapping("/visitors")
    public ResultJson visitors() {
        System.out.println("visitors");
        return null;
    }


    @ApiOperation("登录注册数据")
    @PostMapping("/logonData")
    public ResultJson logonData() {
        System.out.println("logonData");
        return null;
    }

    @ApiOperation("画师认证数据")
    @PostMapping("/painter")
    public ResultJson painter() {
        System.out.println("painter");
        return null;
    }

    @ApiOperation("作品转发数据")
    @PostMapping("/workForwarding")
    public ResultJson workForwarding() {
        System.out.println("workForwarding");
        return null;
    }

}
