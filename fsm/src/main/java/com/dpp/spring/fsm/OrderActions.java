package com.dpp.spring.fsm;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * @author dpp
 * @date 2025/9/29
 * @Description 订单状态机的动作类，定义了订单状态转换时需要执行的业务逻辑
 */
public class OrderActions {
    // 支付成功后执行的业务逻辑（扣库存、生成支付记录等）
    public Action<OrderStatus, OrderEvent> paySuccessAction() {
        return new Action<OrderStatus, OrderEvent>() {
            @Override
            public void execute(StateContext<OrderStatus, OrderEvent> context) {
                // 获取订单ID（实际场景中可从上下文传递参数）
                String orderId = (String) context.getMessage().getHeaders().get("orderId");
                System.out.println("【业务逻辑】订单" + orderId + "支付成功，执行扣减库存、生成支付记录...");
                // 实际业务：调用库存服务、支付服务等
            }
        };
    }

    // 取消订单后执行的业务逻辑（恢复库存、发送取消通知等）
    public Action<OrderStatus, OrderEvent> cancelAction() {
        return new Action<OrderStatus, OrderEvent>() {
            @Override
            public void execute(StateContext<OrderStatus, OrderEvent> context) {
                String orderId = (String) context.getMessage().getHeaders().get("orderId");
                System.out.println("【业务逻辑】订单" + orderId + "已取消，执行恢复库存、发送取消通知...");
            }
        };
    }

    // 发货后执行的业务逻辑（更新物流信息、通知用户等）
    public Action<OrderStatus, OrderEvent> shipAction() {
        return new Action<OrderStatus, OrderEvent>() {
            @Override
            public void execute(StateContext<OrderStatus, OrderEvent> context) {
                String orderId = (String) context.getMessage().getHeaders().get("orderId");
                System.out.println("【业务逻辑】订单" + orderId + "已发货，更新物流信息、通知用户...");
            }
        };
    }
}
