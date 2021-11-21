package com.dpp.observer;

/**
 * 用户登录监听者
 * @author wydpp
 **/
public class SmsUserLoginObserver implements IUserLoginObserver {

    @Override
    public void login(String userName) {
        System.out.println("监听者->用户:"+userName+" 已登录，发送通知短信!");
    }
}
