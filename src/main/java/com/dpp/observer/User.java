package com.dpp.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wydpp
 **/
public class User {

    private List<IUserLoginObserver> loginObservers = new ArrayList<>();

    public void login(final String userName) {
        System.out.println("被监听者->用户：" + userName + " 登录!");
        notifyAllLoginObserver(userName);
    }

    /**
     * 通知所有登录的监听者
     * @param userName
     */
    private void notifyAllLoginObserver(String userName){
        loginObservers.forEach(observer -> observer.login(userName));
    }

    public void addLoginObserver(IUserLoginObserver loginObserver){
        loginObservers.add(loginObserver);
    }

    public void removeLoginObserver(IUserLoginObserver loginObserver){
        loginObservers.remove(loginObserver);
    }

}
