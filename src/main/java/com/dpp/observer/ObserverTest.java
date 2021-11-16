package com.dpp.observer;

/**
 * @author wydpp
 **/
public class ObserverTest {

    public static void main(String[] args) {
        SmsUserLoginObserver smsUserLoginObserver = new SmsUserLoginObserver();
        UserLoginService user = new UserLoginService();
        user.addLoginObserver(smsUserLoginObserver);
        user.login("jerry");
        user.login("sea");

    }
}
