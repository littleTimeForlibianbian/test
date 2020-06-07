package com.example.lixc.service;

public interface IAsyncService {

    /**
     * 异步发送邮件
     * @param to 
     * @param subject
     * @param content
     */
    void sendEmailAsync(String to, String subject, String content);
}
