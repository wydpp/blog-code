package com.dpp.state;

/**
 * 订单创建状态
 *
 * @author wydpp
 */
public class PayedState extends State {

    public PayedState(Order order) {
        super(order);
    }

    @Override
    void pay() {
        System.out.println("订单已支付，无需支付！");
    }

    @Override
    void cancel() {
        System.out.println("订单已支付，不可以取消订单");
    }
}
