package com.dpp.spring.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventBus {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishUserLoginEvent(final String userName) {
        System.out.println("Publishing custom event. ");
        UserLoginEvent userLoginEvent = new UserLoginEvent(this);
        userLoginEvent.setUserName(userName);
        applicationEventPublisher.publishEvent(userLoginEvent);
    }
}
