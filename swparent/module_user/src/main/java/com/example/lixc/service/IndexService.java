package com.example.lixc.service;

import com.example.lixc.util.ResultJson;
import org.springframework.web.multipart.MultipartFile;

public interface IndexService {

    ResultJson uploadImage(MultipartFile[] files);

}
