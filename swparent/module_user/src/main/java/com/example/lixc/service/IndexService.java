package com.example.lixc.service;

import com.example.lixc.entity.WComment;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.WorkBack;
import com.example.lixc.vo.query.WCommentQuery;
import com.example.lixc.vo.query.WorkQuery;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartFile;

public interface IndexService {

    ResultJson uploadImage(MultipartFile[] files);

    ResultJson uploadWork(WorkQuery workQuery);

    ResultJson selectAllWorkLabels();

    Page<WorkBack> workList(WorkQuery workQuery, String more);

    ResultJson workDetail(WorkQuery workQuery);

    ResultJson createHistory(String content);

    ResultJson addWebsite(String website);

    //关注
    ResultJson focus(String toUserId);

    ResultJson like(String workId);

    ResultJson comment(WCommentQuery commentQuery);

    ResultJson commentLike(int id);

    ResultJson commentDel(int id);
}
