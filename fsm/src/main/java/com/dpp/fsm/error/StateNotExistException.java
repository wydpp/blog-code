package com.dpp.fsm.error;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description
 */
public class StateNotExistException extends RuntimeException{
    public StateNotExistException() {
    }

    public StateNotExistException(String message) {
        super(message);
    }
}
