package com.featherworld.project.common.config;

import com.featherworld.project.common.interceptor.BoardTypeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** 어떤 요청을 가로챌지 결정하는 설정 클래스
 * @author Jiho
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public BoardTypeInterceptor boardTypeInterceptor() {
        return new BoardTypeInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
