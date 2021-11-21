package com.dpp.spring.observer;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 用户登录监听者
 *
 * @author wydpp
 **/
@Component
public class SmsUserLoginListener implements ApplicationListener<UserLoginEvent> {

    @Override
    public void onApplicationEvent(UserLoginEvent event) {
        System.out.println("监听者->用户:" + event.getUserName() + " 已登录，发送通知短信!");
        System.out.println("listener:"+Thread.currentThread().getName()+Thread.currentThread().getId());
        throw new RuntimeException("ListenerException");
    }
}
