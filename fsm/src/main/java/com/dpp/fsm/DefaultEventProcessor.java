package com.dpp.fsm;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description
 */
public class DefaultEventProcessor implements EventProcessor {
    @Override
    public void exitOldState(State state, Event event) {
        System.out.printf("exitOldState, state=%s, event=%s", state, event);
    }

    @Override
    public void enterNewState(State state, Event event) {
        System.out.printf("enterNewState, state=%s, event=%s", state, event);
    }
}
