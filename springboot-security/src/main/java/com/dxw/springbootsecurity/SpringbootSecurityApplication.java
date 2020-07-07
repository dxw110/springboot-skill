package com.dxw.springbootsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
*@Description: 
*@Author: DCool
*@date:
*/
@SpringBootApplication
@MapperScan({"com.dxw.springbootsecurity.user.mapper"})
public class SpringbootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurityApplication.class, args);
    }

}
