package com.sunlu.chengxin.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor h = paramsInterceptorInit();
        System.out.println(h);
        List<String> list = new ArrayList<>();
        list.add("/chengxin/user/login");
        list.add("/chengxin/user/forgetPassword");
        //   /chengxin/* 是只拦截一层，如 /chengxin/xxx  /** 是拦截多层  如：/chengxin/xxx/xxx
        registry.addInterceptor(paramsInterceptorInit()).addPathPatterns("/chengxin/**").excludePathPatterns(list);
        super.addInterceptors(registry);
    }
    @Bean
    public ParamsInterceptor paramsInterceptorInit() {
        return new ParamsInterceptor();
    }
}
