package com.example.lixc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.example.lixc.mapper")
@SpringBootApplication()
@EnableAsync
public class ModuleUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleUserApplication.class, args);
    }

}
