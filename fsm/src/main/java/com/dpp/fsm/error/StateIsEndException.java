package com.dpp.fsm.error;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description
 */
public class StateIsEndException extends RuntimeException{
    public StateIsEndException() {
    }

    public StateIsEndException(String message) {
        super(message);
    }
}
