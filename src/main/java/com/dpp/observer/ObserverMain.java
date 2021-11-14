package com.dpp.observer;

/**
 * @author wydpp
 **/
public class ObserverMain {

    public static void main(String[] args) {
        SmsUserLoginObserver smsUserLoginObserver = new SmsUserLoginObserver();
        User user = new User();
        user.addLoginObserver(smsUserLoginObserver);
        user.login("jerry");

    }
}
