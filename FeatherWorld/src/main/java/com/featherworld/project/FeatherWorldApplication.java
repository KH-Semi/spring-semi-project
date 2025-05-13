package com.featherworld.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// Spring Security에서 기본 제공해주는 페이지 사용하지 않기 위함
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class FeatherWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeatherWorldApplication.class, args);
	}

}
