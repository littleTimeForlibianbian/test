package com.example.lixc.service.impl;

import com.example.lixc.service.IAsyncService;
import com.example.lixc.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class AsyncServiceImpl implements IAsyncService {

    @Autowired
    private IMailService mailService;

    @Async
    @Override
    public void sendEmailAsync(String to, String subject, String content) {
        mailService.sendHtmlMail(to, subject, content);
    }
}
