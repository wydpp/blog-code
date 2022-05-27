package com.dpp.state;

import com.sun.org.apache.xpath.internal.operations.Or;

/**
 * 订单类
 * @author wydpp
 */
public class Order {

    private State state;

    public Order() {
        this.state = new CreatedState(this);
    }

    public void changeState(State state) {
        this.state = state;
    }

    void pay() {
        state.pay();
    }

    void cancel() {
        state.cancel();
    }

    public static void main(String[] args) {
        Order order = new Order();
        //order.pay();
        order.cancel();
        order.pay();
    }
}
