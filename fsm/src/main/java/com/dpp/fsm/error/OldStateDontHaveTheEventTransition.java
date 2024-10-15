package com.dpp.fsm.error;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description
 */
public class OldStateDontHaveTheEventTransition extends RuntimeException{
    public OldStateDontHaveTheEventTransition() {
    }

    public OldStateDontHaveTheEventTransition(String message) {
        super(message);
    }
}
