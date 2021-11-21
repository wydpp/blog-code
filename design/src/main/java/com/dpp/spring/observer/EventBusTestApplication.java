package com.dpp.spring.observer;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootApplication
public class EventBusTestApplication implements ApplicationContextAware {

    public static void main(String[] args) {
        SpringApplication.run(EventBusTestApplication.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserLoginService userLoginService = applicationContext.getBean(UserLoginService.class);
        userLoginService.login("jerry");
        System.out.println("EventBusApplication:"+Thread.currentThread().getName()+Thread.currentThread().getId());
    }
}
