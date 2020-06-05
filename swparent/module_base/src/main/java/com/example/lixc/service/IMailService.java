package com.example.lixc.service;

public interface IMailService {

    /**
     * 发送简单邮件
     *
     * @param to      接收人
     * @param subject 主题项
     * @param content 正文/内容
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容（可以包含<html>等标签）
     */
    void sendHtmlMail(String to, String subject, String content);
    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件路径
     */
    void sendAttachmentMail(String to, String subject, String content, String filePath);
    /**
     * 发送带图片的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 文本
     * @param rscPath 图片路径
     * @param rscId 图片ID，用于在<img>标签中使用，从而显示图片
     */
    void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);

}
