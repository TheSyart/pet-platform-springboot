package com.pets.config;

import com.pets.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    @Value("${spring.web.resources.static-locations}")
    private String photoStoragePath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/server-resource/**")
                .excludePathPatterns("/error/**")
                // 客户手机发送短信
                .excludePathPatterns("/customer/sendSms");

    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 允许访问 环境变量下/server-resource 目录及其子目录下的文件
        registry.addResourceHandler("/server-resource/**")
                .addResourceLocations("file:" + photoStoragePath + "/");

    }

}
