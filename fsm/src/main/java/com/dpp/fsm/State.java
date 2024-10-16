package com.dpp.fsm;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description 订单状态
 */
public enum State {
    /**
     * 待支付
     */
    StateWaitPay,
    //已取消
    StateCancel,
    //待确认
    StateWaitConfirm,
    /**
     * 待发货
     */
    StateWaitDelive,
    /**
     * 售后中-退款
     */
    StateRefund,
    /**
     * 待收货
     */
    StateWaitReceive,
    /**
     * 已签收
     */
    StateSigned,
    /**
     * 售后中-退货退款
     */
    StateGoodsRefund,
    //已支付
    StatePayied,
    /**
     * 已完成
     */
    StateCompleted,
    ;

}
