package com.dpp.spring.fsm;

/**
 * @author dpp
 * @date 2025/9/29
 * @Description 订单事件
 */
public enum OrderEvent {
    PAY,//支付
    SHIP,//发货
    COMPLETE,//完成
    CANCEL,//取消
}
