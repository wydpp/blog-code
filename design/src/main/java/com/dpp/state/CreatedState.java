package com.dpp.state;

/**
 * 订单创建状态
 * @author wydpp
 */
public class CreatedState extends State{

    public CreatedState(Order order) {
        super(order);
    }

    @Override
    void pay() {
        System.out.println("进行支付！");
        order.changeState(new PayedState(order));
    }

    @Override
    void cancel() {
        System.out.println("取消订单");
        order.changeState(new CancelState(order));
    }
}
