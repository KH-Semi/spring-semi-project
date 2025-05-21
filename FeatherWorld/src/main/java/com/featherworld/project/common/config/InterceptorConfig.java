package com.featherworld.project.common.config;

import com.featherworld.project.common.interceptor.MemberInterceptor;
import com.featherworld.project.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public MemberInterceptor memberInterceptor() {
        return new MemberInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(memberInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/js/**", "/images/**", "/favicon.ico",
                        "/member/**", "/email/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
