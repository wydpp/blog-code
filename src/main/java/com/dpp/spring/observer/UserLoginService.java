package com.dpp.spring.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserLoginService {

    @Autowired
    private EventBus eventBus;

    public void login(String userName) {
        System.out.println("被监听者->用户：" + userName + " 登录!");
        eventBus.publishUserLoginEvent(userName);
    }
}
