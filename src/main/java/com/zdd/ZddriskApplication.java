package com.zdd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zdd.risk")
public class ZddriskApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZddriskApplication.class, args);
	}
}
