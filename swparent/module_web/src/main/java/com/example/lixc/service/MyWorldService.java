package com.example.lixc.service;

import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.VersionSpoQuery;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/1 17:35
 */
public interface MyWorldService {
    /**
     * @return
     */
    ResultJson newMessage();

    ResultJson newMessageRead(int messageId);

    ResultJson myLike();

    ResultJson myFocus();

    ResultJson news();

    ResultJson systemMessage();

    ResultJson addFeedBack(String content);

    /**
     * 查询反馈优化列表
     *
     * @return
     */
    ResultJson feedBackOptList(VersionSpoQuery versionSpoQuery);

    ResultJson versionSpoiler(VersionSpoQuery versionSpoQuery);

    ResultJson allMessage();

    ResultJson queryCount(Integer userId);

    ResultJson feedBackLike(Integer id);

    ResultJson feedBackList();

    /**
     * 查询 我的世界主页中所有的推荐消息和关注的人发布作品消息
     *
     * @param userId 接受者id
     * @return
     */
    ResultJson recommendMessage(int userId);

    ResultJson systemMessageDetail(int id);
}
