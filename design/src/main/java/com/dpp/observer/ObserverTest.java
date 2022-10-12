package com.dpp.observer;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wydpp
 **/
public class ObserverTest {

    public static void main(String[] args) {
        Thread t = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("sleep结束:time="+new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //t.setDaemon(true);
        t.start();
        System.out.println("main方法结束!time="+new Date());
    }
}
