package com.dpp.state;

/**
 * 订单创建状态
 *
 * @author wydpp
 */
public class CancelState extends State {

    public CancelState(Order order) {
        super(order);
    }

    @Override
    void pay() {
        System.out.println("订单已取消，不可以进行支付！");
    }

    @Override
    void cancel() {
        System.out.println("订单已取消");
    }
}
