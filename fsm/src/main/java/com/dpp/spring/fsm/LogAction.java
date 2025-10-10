package com.dpp.spring.fsm;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * @author dpp
 * @date 2025/9/29
 * @Description
 */
public class LogAction implements Action<OrderStatus, OrderEvent> {
    @Override
    public void execute(StateContext<OrderStatus, OrderEvent> stateContext) {
        System.out.println("LogAction execute from "
                + stateContext.getSource() +
                " to "
                + stateContext.getTarget()
                + " with event "
                + stateContext.getEvent()
        );
    }
}
