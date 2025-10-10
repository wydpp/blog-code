package com.dpp.spring.fsm;

/**
 * @author dpp
 * @date 2025/9/29
 * @Description 订单状态
 */
public enum OrderStatus {
    //待支付 -》已支付 -》已发货 -》已完成
    //待支付 -》已取消
    WAIT_PAY,//待支付
    PAID,//已支付
    SHIPPED,//已发货
    COMPLETED,//已完成
    CANCELLED,//已取消
}
