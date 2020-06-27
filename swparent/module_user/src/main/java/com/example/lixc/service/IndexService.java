package com.example.lixc.service;

import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.WorkBack;
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
}
