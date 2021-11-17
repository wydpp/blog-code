package com.dpp.spring.observer;

import org.springframework.context.ApplicationEvent;

public class UserLoginEvent extends ApplicationEvent {
    private String userName;

    public UserLoginEvent(Object source) {
        super(source);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
