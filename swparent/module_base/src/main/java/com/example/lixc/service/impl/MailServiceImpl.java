package com.example.lixc.service.impl;

import com.example.lixc.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@Slf4j
public class MailServiceImpl implements IMailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        simpleMailMessage.setFrom(from);
        try {
            mailSender.send(simpleMailMessage);
            log.info("Text邮件已经发送。");
        } catch (Exception e) {
            log.error("发送Text邮件时发生异常！", e);
        }
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.addTo(to);
            messageHelper.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(content, true);
            messageHelper.setFrom(from);
            mailSender.send(mimeMessage);
            log.info("sendHtmlMail>>>邮件已经发送");
        } catch (Exception ex) {
            log.info("sendHtmlMail>>>catch exception: {}", ex.getMessage(), ex);
        }

    }

    @Override
    public void sendAttachmentMail(String to, String subject, String content, String filePath) {

    }

    @Override
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessagingException {
        //创建message
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //发件人
        helper.setFrom(from);
        //收件人
        helper.setTo(to);
        //标题
        helper.setSubject(subject);
        //true指的是html邮件，false指的是普通文本
        helper.setText(content, true);
        //添加图片
        FileSystemResource file = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId, file);
        //发送邮件
        mailSender.send(message);
    }
}
