package com.example.lixc.service;

import com.example.lixc.util.ResultJson;

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

    ResultJson feedBackOpt();

    ResultJson versionSpoiler();

    ResultJson allMessage();

    ResultJson queryCount();

    ResultJson feedBackLike(String id);

    ResultJson feedBackList();
}
