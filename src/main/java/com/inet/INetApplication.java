package com.inet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.inet.code.mapper")
/**
 * 主方法
 * @author HCY
 * @since 2020-11-07
 */
public class INetApplication {

    public static void main(String[] args) {
        SpringApplication.run(INetApplication.class, args);
    }

}
