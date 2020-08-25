package com.example.lixc.controller.portal;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.lixc.config.InitConfig;
import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.dto.UserInfoDTO;
import com.example.lixc.mapper.UserMapper;
import com.example.lixc.service.IAsyncService;
import com.example.lixc.service.UserPortalService;
import com.example.lixc.service.WorkService;
import com.example.lixc.util.RedisContents;
import com.example.lixc.util.RedisPoolUtil;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.back.WorkBack;
import com.example.lixc.vo.query.UserQuery;
import com.example.lixc.vo.query.WorkQuery;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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


    @Autowired
    private WorkService workService;

    /**
     * 用户注册接口
     *
     * @param user
     * @return
     */
    @PostMapping("/registerUser")
    @ApiOperation("用户注册")
    public ResultJson registerUser(@RequestBody UserQuery user, HttpServletRequest request) {
        try {
            return userPortalService.registerUser(user, request);
        } catch (Exception e) {
            log.error("用户注册发生异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("用户注册发生异常");
        }

    }

    @GetMapping("/test")
    public ResultJson test() {
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
     * 用戶登录接口
     *
     * @return
     */
    @GetMapping("/logout")
    @ApiOperation("退出登录")
    public ResultJson logout() {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            //#2.清空当前的`SecurityContext`
            context.setAuthentication(null);
            SecurityContextHolder.clearContext();
            log.info("退出登录成功");
            return ResultJson.buildSuccess("退出登录成功");
        } catch (Exception e) {
            log.error("退出登录发生异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("退出登录发生异常");
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

    /**
     * 上传图片 file
     *
     * @param file
     * @return
     */
    @ApiOperation("上传图片")
    @PostMapping("/uploadImage")
    public ResultJson uploadImage(@RequestParam("file") MultipartFile file) {
        MultipartFile[] files = new MultipartFile[]{file};
        return workService.uploadImage(files);
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


    /**
     * 查询作品列表  按照时间进行倒叙排序
     * 查询的是普通上传作品 非认证作品
     *
     * @param query
     * @return
     */
    @ApiOperation("查询作品列表")
    @PostMapping("/workList")
    public Page<WorkBack> workList(WorkQuery query) {
        try {
            return workService.workList(query);
        } catch (Exception e) {
            log.error("workList exception:{}", e.getMessage());
            return new Page<>();
        }
    }

    /**
     * 搜搜作品列表
     *
     * @param query
     * @return
     */
    @ApiOperation("搜索作品列表")
    @PostMapping("/searchWorkList")
    public ResultJson searchWorkList(WorkQuery query) {
        try {
            return ResultJson.buildSuccess(workService.searchWorkList(query));
        } catch (Exception e) {
            log.error("searchWorkList exception:{}", e.getMessage());
            return ResultJson.buildError("搜索作品列表异常");
        }
    }


    /**
     * 作品详情
     *
     * @param workId
     * @return
     */
    @ApiOperation("作品详情")
    @GetMapping("/workDetail")
    public ResultJson workDetail(Integer workId) {
        try {
            return workService.workDetail(workId);
        } catch (Exception e) {
            log.error("workList exception:{}", e.getMessage());
            return ResultJson.buildError("获取作品详情发生异常");
        }
    }

    /**
     * 其余作品
     *
     * @param query
     * @return
     */
    @ApiOperation("其余作品")
    @PostMapping("/other")
    public ResultJson other(WorkQuery query) {
        try {
            return workService.other(query);
        } catch (Exception e) {
            log.error("workList exception:{}", e.getMessage());
            return ResultJson.buildError("获取其余作品发生异常");
        }
    }


}
