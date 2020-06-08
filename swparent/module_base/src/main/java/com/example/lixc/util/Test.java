package com.example.lixc.util;

import org.springframework.mail.javamail.MimeMailMessage;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/4 17:12
 */
public class Test {
    public static void main(String[] args) throws IOException, MessagingException {
        Properties properties = new Properties();
        Session session = Session.getInstance(properties);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("1193096107@qq.com", "nicky", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("2643574705@qq.com", "95", "UTF-8"));
        message.setSubject("test send Email");
        message.setContent("this is the content", "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        OutputStream ous = new FileOutputStream("MyEmail.eml");
        message.writeTo(ous);
        ous.flush();
        ous.close();
    }
}
