package com.dpp.fsm;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description 事件处理器
 */
public interface EventProcessor {
    /**
     * 离开旧状态
     * @param state
     * @param event
     */
    void exitOldState(State state, Event event);

    /**
     * 进入新状态
     * @param state
     * @param event
     */
    void enterNewState(State state, Event event);
}
