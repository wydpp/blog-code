package com.dpp.fsm;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description 动作
 */
public interface Action {

    ActionResult action(State from, Event event, State to);
}
