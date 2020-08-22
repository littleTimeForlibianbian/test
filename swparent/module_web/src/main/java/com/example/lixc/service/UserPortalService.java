package com.example.lixc.service;

import com.example.lixc.entity.Tag;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.AdminUserBack;
import com.example.lixc.vo.query.AdminUserQuery;
import com.example.lixc.vo.query.UserQuery;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 13:51
 */
public interface UserPortalService {
    /*************portal start***********************/

    /**
     * 用户注册接口
     *
     * @param userQuery 用户对象
     * @return ResultJson
     */
    ResultJson registerUser(UserQuery userQuery, HttpServletRequest request);

    /**
     * 用户登录接口
     *
     * @param userQuery 用户对象
     * @return ResultJson
     */
    ResultJson logon(@Param("userQuery") UserQuery userQuery, @Param("request") HttpServletRequest request);


    /**
     * 用户注册激活接口
     *
     * @param param 用户昵称
     * @return ResultJson
     */
    ResultJson activeRegister(String param);

    /**
     * 忘记密码
     *
     * @return
     */
    ResultJson forgetPassword(String email);


    /**
     * 重置密码
     *
     * @param userQuery
     * @return
     */
    ResultJson resetPassword(UserQuery userQuery);


    /**
     * 选择标签
     *
     * @param tags
     * @return
     */
    ResultJson chooseTags(String tags,Integer userId);

    /**
     * 查询所有的用户标签
     *
     * @return
     */
    ResultJson allTags();

    /**
     * 个人主页获取用户详情接口
     * 作品详情页获取作者详情
     *
     * @param userQuery
     * @return
     */
    ResultJson getUserInfo(UserQuery userQuery);

    ResultJson updateUserAttr(UserQuery userQuery);


    /**
     * @param picString 头像base64
     * @param userId    当前用户id
     * @return
     */
    ResultJson updateUserHeadImage(String picString, Integer userId);


    /**
     * 查询全部的普通用户的id集合
     *
     * @return
     */
    List<Integer> selectNormalUserIdList();

}
