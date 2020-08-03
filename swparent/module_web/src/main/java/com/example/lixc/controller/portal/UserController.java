package com.example.lixc.controller.portal;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.lixc.config.InitConfig;
import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.dto.UserInfoDTO;
import com.example.lixc.mapper.UserMapper;
import com.example.lixc.service.IAsyncService;
import com.example.lixc.service.UserPortalService;
import com.example.lixc.util.RedisContents;
import com.example.lixc.util.RedisPoolUtil;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.UserQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lixc
 * @Description 用户处理类
 * @createTime 2020/6/1 12:43
 */
@Api("用户管理类")
@RestController
@RequestMapping("/public/user")
@Slf4j
public class UserController {

    @Autowired
    private UserPortalService userPortalService;


    /**
     * 用户注册接口
     *
     * @param user
     * @return
     */
    @PostMapping("/registerUser")
    @ApiOperation("用户注册")
    public ResultJson registerUser(@RequestBody UserQuery user) {
        try {
            return userPortalService.registerUser(user);
        } catch (Exception e) {
            log.error("用户注册发生异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("用户注册发生异常");
        }

    }

    @Autowired
    private IAsyncService asyncService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/test")
    public ResultJson test(int userId) {
        return ResultJson.buildSuccess(SysConfigUtil.getLoginUserId());
    }


    /**
     * 注册激活接口
     *
     * @param param 昵称base64
     * @return
     */
    @GetMapping("/activeRegister")
    @ApiOperation("用户注册-激活")
    public ResultJson activeRegister(String param) {
        try {
            return userPortalService.activeRegister(param);
        } catch (Exception e) {
            log.error("用户激活发生异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("用户激活发生异常");
        }
    }

    /**
     * 用戶登录接口
     *
     * @param userQuery 登录对象
     * @return
     */
    @PostMapping("/logon")
    @ApiOperation("用户登录")
    public ResultJson logon(UserQuery userQuery, HttpServletRequest request) {
        try {
            return userPortalService.logon(userQuery, request);
        } catch (Exception e) {
            log.error("用户登录发生异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("用户登录发生异常");
        }
    }


    /**
     * 忘记密码接口
     *
     * @return
     */
    @PostMapping("/forgetPassword")
    @ApiOperation("用户登录")
    public ResultJson forgetPassword(String email) {
        try {
            return userPortalService.forgetPassword(email);
        } catch (Exception e) {
            log.error("忘记密码发送邮件异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("忘记密码发送邮件异常");
        }
    }


    @PostMapping("/resetPassword")
    @ApiOperation("忘记密码")
    public ResultJson resetPassword(UserQuery userQuery) {
        try {
            return userPortalService.resetPassword(userQuery);
        } catch (Exception e) {
            log.error("重置密码发生异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("重置密码发生异常");
        }
    }

    /**
     * 获取用户详情接口
     *
     * @return
     */
    @GetMapping("/getUserInfo")
    @ApiOperation("获取用户详情接口")
    public ResultJson getUserInfo(UserQuery userQuery) {
        try {
            return userPortalService.getUserInfo(userQuery);
        } catch (Exception e) {
            log.error("获取用户详情接口:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("获取用户详情接口异常");
        }
    }

    //设置用户名片
    @PostMapping("/updateUserAttr")
    @ApiOperation("设置用户名片接口")
    public ResultJson updateUserAttr(UserQuery userQuery) {
        try {
            return userPortalService.updateUserAttr(userQuery);
        } catch (Exception e) {
            log.error("设置用户名片异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("设置用户名片异常");
        }
    }

    //设置用户头像
    @PostMapping("/updateUserHeadImage")
    @ApiOperation("设置用户头像")
    public ResultJson updateUserHeadImage(@RequestParam("picString") String picString, @RequestParam("userId") Integer userId) {
        return userPortalService.updateUserHeadImage(picString, userId);
    }

    /**
     * 查询当前用户是否是画师，只有画师才能看到myWorld
     *
     * @return
     */
//    @PostMapping("/isPainter")
//    @ApiOperation("是否是画师")
//
//    public ResultJson isPainter() {
//        try {
//            return userPortalService.isPainter();
//        } catch (Exception e) {
//            log.error("重置密码发生异常:{}", e.getMessage());
//            e.printStackTrace();
//            return ResultJson.buildError("重置密码发生异常");
//        }
//    }

    /**
     * 选择标签
     *
     * @return
     */
    @PostMapping("/chooseTags")
    @ApiOperation("选择标签")
    public ResultJson chooseTags(String ids) {
        try {
            return userPortalService.chooseTags(ids);
        } catch (Exception e) {
            log.error("重置密码发生异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("重置密码发生异常");
        }
    }

    /**
     * 查询所有标签
     *
     * @return
     */
    @PostMapping("/allTags")
    @ApiOperation("查询所有标签")
    public ResultJson allTags() {
        try {
            return userPortalService.allTags();
        } catch (Exception e) {
            log.error("查询所有标签异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("查询所有标签异常");
        }
    }

}
