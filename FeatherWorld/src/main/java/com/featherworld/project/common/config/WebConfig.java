package com.featherworld.project.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /uploads/** URL로 요청 시 실제 파일 경로로 매핑
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/upload/");
    }
}
