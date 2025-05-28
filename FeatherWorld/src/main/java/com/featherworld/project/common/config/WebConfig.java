package com.featherworld.project.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 프로필 이미지 저장 디렉토리: C:/uploadFiles/profile/
        // 웹에서 접근할 경로: /myPage/profile/**

        registry.addResourceHandler("/myPage/profile/**") // 웹에서 접근할 경로
                .addResourceLocations("file:///C:/uploadFiles/profile/"); // 실제 이미지가 저장된 경로
    }
}
