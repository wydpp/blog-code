package com.dpp.spring;

import com.dpp.spring.service.BlogService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlogService blogService = applicationContext.getBean(BlogService.class);
        System.out.println(blogService.getBlog("100"));
    }
}
