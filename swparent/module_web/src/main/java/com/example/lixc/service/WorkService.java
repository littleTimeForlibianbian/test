package com.example.lixc.service;

import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.WorkBack;
import com.example.lixc.vo.query.WCommentQuery;
import com.example.lixc.vo.query.WorkQuery;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface WorkService {

    ResultJson uploadImage(MultipartFile[] files);

    ResultJson uploadWork(WorkQuery workQuery);

    ResultJson selectAllWorkLabels();

    Page<WorkBack> workList(WorkQuery workQuery);

    ResultJson workDetail(Integer workId);

    ResultJson selectCommentList(Integer workId);

//    ResultJson createHistory(String content);
//
//    ResultJson addWebsite(String website);

    //关注
    ResultJson focus(Integer toUserId);

    ResultJson queryFocus(Integer toUserId);

    ResultJson like(Integer workId, Integer fromUserId);

    ResultJson comment(WCommentQuery commentQuery);

    ResultJson commentLike(int id);

    ResultJson commentDel(int id);

    ResultJson workCheck(WorkQuery workQuery, HttpServletRequest request);

    ResultJson other(WorkQuery query);

    //向所有关注我的人 推荐此作品
    ResultJson recommend(Integer workId);

    ResultJson reportList();

    ResultJson report(String ids, Integer workId);

    //以base64的格式上传图片
    ResultJson uploadImageBase64(String data);

    //使用ftpUtil上传工具上传到图片服务器
    ResultJson uploadImageToServer(String base64);

    ResultJson getUserInfoByWorkId(Integer workId);

    ResultJson workDel(Integer workId);

    /**
     * 搜索作品列表
     *
     * @param query
     * @return
     */
    List<WorkBack> searchWorkList(WorkQuery query);
}
