package com.dpp.fsm;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description 事件
 */
public enum Event {
    /**
     * 支付
     */
    EventPay,
    /**
     * 取消
     */
    EventCancel,
    /**
     * 支付确认
     */
    EventPayConfirm,
    ;
}
