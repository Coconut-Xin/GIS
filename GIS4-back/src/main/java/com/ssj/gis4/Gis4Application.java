package com.ssj.gis4;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
//@MapperScan("com.ssj.gis4.mapper")

@SpringBootApplication
@EnableScheduling
public class Gis4Application {

    public static void main(String[] args) {
        SpringApplication.run(Gis4Application.class, args);
    }
}
