package com.example.lixc;

import com.example.lixc.service.IMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ModuleBaseApplicationTests {

    @Autowired
    private IMailService mailService;

    @Test
    void contextLoads() {
    }

    @Test
    void testSendEmail(){
        String to  = "li_xianchun@itrus.com.cn";
        String subject = "test send email ";
        String content = "1234";
        mailService.sendHtmlMail(to,subject,content);
    }
}
