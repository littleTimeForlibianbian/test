package com.example.lixc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * TODO 邮件发送暂时使用异步线程的方式来处理，以后使用可以考虑使用mq中间件； 需要增加邮件发送记录表
 */
@MapperScan(basePackages = "com.example.lixc.mapper")
@SpringBootApplication
@EnableAsync
public class ModuleUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleUserApplication.class, args);
    }

}
