package com.example.lixc.service;

import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.WorkQuery;
import org.springframework.web.multipart.MultipartFile;

public interface IndexService {

    ResultJson uploadImage(MultipartFile[] files);

    ResultJson uploadWork(WorkQuery workQuery);

    ResultJson selectAllWorkLabels();

    ResultJson workList(WorkQuery workQuery, boolean more);

    ResultJson workDetail(WorkQuery workQuery);

}
