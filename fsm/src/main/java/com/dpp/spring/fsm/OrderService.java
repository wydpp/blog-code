package com.dpp.spring.fsm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;

/**
 * @author dpp
 * @date 2025/9/29
 * @Description 订单服务
 */
public class OrderService {
    //单例的订单状态机
    //@Autowired
    //@Qualifier("orderStateMachine")
    //private StateMachine<OrderStatus, OrderEvent> orderStateMachine;
    /**
     * 订单状态机工厂
     */
    @Autowired
    private StateMachineFactory<OrderStatus, OrderEvent> stateMachineFactory;

    // 获取或创建订单对应的状态机，每次调用均同步数据库状态，不缓存
    public StateMachine<OrderStatus, OrderEvent> getOrCreateStateMachine(String orderId) {
        // 为订单创建独立状态机，ID 绑定订单ID
        StateMachine<OrderStatus, OrderEvent> stateMachine = stateMachineFactory.getStateMachine(orderId);
        // 订单的状态需要和数据库的订单状态保持一致
        // 构建数据库状态对应的上下文（核心：用DB状态替换默认初始状态）
        StateMachineContext dbContext = getOrderStatusFromDb(orderId);
        // 重置状态机为数据库上下文（关键步骤：同步状态）
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(accessor -> {
                    try {
                        // 重置状态机上下文为数据库状态（SSM 2.2.0 核心操作）
                        accessor.resetStateMachineReactively(dbContext);
                    } catch (Exception e) {
                        throw new RuntimeException("重置状态机上下文失败（订单ID: " + orderId + "）", e);
                    }
                });
        // 启动状态机（此时状态已与数据库一致）
        stateMachine.startReactively();
        return stateMachine;
    }

    private StateMachineContext<OrderStatus, OrderEvent> getOrderStatusFromDb(String orderId) {
        // 从数据库查询订单状态
        // 这里假设数据库查询返回的订单状态为 WAIT_PAY
        return new DefaultStateMachineContext<>(
                OrderStatus.WAIT_PAY, // 当前状态（来自数据库）
                null, // 无触发事件
                null, // 无扩展状态（可按需添加业务数据）
                null  // 无历史状态
        );
    }

    /**
     * 支付订单
     */
    public void payOrder(String orderId) {
        // 2.2.0 版本通过 getState() 是否为 null 判断是否启动
        StateMachine<OrderStatus, OrderEvent> orderStateMachine = getOrCreateStateMachine(orderId);
        Message<OrderEvent> message = MessageBuilder
                .withPayload(OrderEvent.PAY)
                .setHeader("orderId", orderId)
                .build();
        // 发送支付事件
        boolean result = orderStateMachine.sendEvent(message);
        System.out.println("支付订单结果：" + (result ? "成功" : "失败"));
    }

    /**
     * 取消订单
     */
    public void cancelOrder(String orderId) {
        StateMachine<OrderStatus, OrderEvent> orderStateMachine = getOrCreateStateMachine(orderId);
        Message<OrderEvent> message = MessageBuilder
                .withPayload(OrderEvent.CANCEL)
                .setHeader("orderId", orderId)
                .build();
        // 发送取消事件
        boolean result = orderStateMachine.sendEvent(message);
        System.out.println("取消订单结果：" + (result ? "成功" : "失败"));
    }

    /**
     * 发货订单
     */
    public void shipOrder(String orderId) {
        StateMachine<OrderStatus, OrderEvent> orderStateMachine = getOrCreateStateMachine(orderId);
        Message<OrderEvent> message = MessageBuilder
                .withPayload(OrderEvent.SHIP)
                .setHeader("orderId", orderId)
                .build();
        // 发送发货事件
        boolean result = orderStateMachine.sendEvent(message);
        System.out.println("发货订单结果：" + (result ? "成功" : "失败"));
        // 停止状态机（可选，根据业务需求）
        orderStateMachine.stopReactively();
    }

    /**
     * 完成订单
     */
    public void completeOrder(String orderId) {
        StateMachine<OrderStatus, OrderEvent> orderStateMachine = getOrCreateStateMachine(orderId);
        Message<OrderEvent> message = MessageBuilder
                .withPayload(OrderEvent.COMPLETE)
                .setHeader("orderId", orderId)
                .build();
        // 发送完成事件
        boolean result = orderStateMachine.sendEvent(message);
        System.out.println("完成订单结果：" + (result ? "成功" : "失败"));
    }
}
