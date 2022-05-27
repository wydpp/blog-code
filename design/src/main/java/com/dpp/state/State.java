package com.dpp.state;

/**
 * @author wydpp
 */
public abstract class State {
    protected Order order;

    public State(Order order) {
        this.order = order;
    }

    /**
     * 付款
     */
    abstract void pay();

    /**
     * 取消订单
     */
    abstract void cancel();
}
