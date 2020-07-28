package com.example.lixc.service.impl;

import com.example.lixc.service.IAsyncService;
import com.example.lixc.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;


@Service
public class AsyncServiceImpl implements IAsyncService {

    @Autowired
    private IMailService mailService;

    @Async
    @Override
    public void sendHtmlEmailAsync(String to, String subject, String content) {
        mailService.sendHtmlMail(to, subject, content);
    }


    @Async
    @Override
    public void sendSimpleEmailAsync(String to, String subject, String content) {
        mailService.sendSimpleMail(to, subject, content);
    }

    @Async
    @Override
    public void sendImageEmailAsync(String to, String subject, String content, String rscPath, String rscId) throws MessagingException {
        mailService.sendInlineResourceMail(to, subject, content, rscPath, rscId);
    }
}
