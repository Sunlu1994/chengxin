package com.sunlu.chengxin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
/**
 * 打包成war到外置tomcat运行 需要：启动类继承SpringBootServletInitializer实现configure：
 */
//public class ChengxinApplication extends SpringBootServletInitializer{
//    public static ApplicationContext applicationContext;
//    public static void main(String[] args) {
//        applicationContext = SpringApplication.run(ChengxinApplication.class, args);
//    }
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(ChengxinApplication.class);
//    }
//
//}
public class ChengxinApplication{
    public static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(ChengxinApplication.class, args);
    }
}

