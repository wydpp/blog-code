package com.dpp.fsm;

import java.util.concurrent.TimeUnit;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description
 */
public class DefaultLogAction implements Action{
    @Override
    public ActionResult action(State from, Event event, State to) {
        System.out.printf("\n默认的action执行开始\n");
        System.out.println("业务逻辑......");
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("默认的action执行结束");
        return new ActionResult(true,"success");
    }
}
