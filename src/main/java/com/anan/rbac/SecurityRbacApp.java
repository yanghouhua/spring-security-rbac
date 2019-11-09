package com.anan.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.anan.rbac.dao")
public class SecurityRbacApp {

    public static void main(String[] args) {
        SpringApplication.run(SecurityRbacApp.class,args);
    }
}
