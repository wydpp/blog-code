package com.dpp.fsm;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description 订单状态
 */
public enum State {
    //待支付
    StateWaitPay,
    //已取消
    StateCancel,
    //待确认
    StateWaitConfirm,
    //已支付
    StatePayied,

    ;

}
