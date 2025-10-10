package com.dpp.spring.fsm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 * @author dpp
 * @date 2025/9/29
 * @Description 订单状态机配置
 * 通过 @Configuration 和 @EnableStateMachine 注解配置状态机，定义状态流转规则：
 */
@Configuration
@EnableStateMachineFactory
public class OrderStateMachineFactoryConfig extends StateMachineConfigurerAdapter<OrderStatus, OrderEvent> {

    @Autowired
    private OrderActions orderActions; // 注入业务动作类

    //配置状态机
    @Override
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderEvent> states) throws Exception {
        states
                .withStates()
                .initial(OrderStatus.WAIT_PAY) //设置初始状态为待支付
                .states(EnumSet.allOf(OrderStatus.class)) //添加所有状态
                .end(OrderStatus.COMPLETED)
                .end(OrderStatus.CANCELLED);
    }

    //配置状态机转换规则
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderEvent> transitions) throws Exception {
        transitions
                //待支付-》已支付
                .withExternal()
                .source(OrderStatus.WAIT_PAY) //从待支付状态
                .target(OrderStatus.PAID) //转换到已支付状态
                .event(OrderEvent.PAY) //触发支付事件
                .action(orderActions.paySuccessAction()) //执行支付成功动作
                //待支付-》已取消
                .and()
                .withExternal()
                .source(OrderStatus.WAIT_PAY) //从待支付状态
                .target(OrderStatus.CANCELLED) //转换到已取消状态
                .event(OrderEvent.CANCEL) //触发取消事件
                .action(orderActions.cancelAction()) //执行取消动作
                //已支付-》已发货
                .and()
                .withExternal()
                .source(OrderStatus.PAID) //从已支付状态
                .target(OrderStatus.SHIPPED) //转换到已发货状态
                .event(OrderEvent.SHIP) //触发发货事件
                .action(orderActions.shipAction()) //执行发货动作
                //已发货-》已完成
                .and()
                .withExternal()
                .source(OrderStatus.SHIPPED) //从已发货状态
                .target(OrderStatus.COMPLETED) //转换到已完成状态
                .event(OrderEvent.COMPLETE) //触发完成事件
                .action(new LogAction()); //执行默认日志操作
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStatus, OrderEvent> config) throws Exception {
        config
                .withConfiguration()
                .listener(listener()); // 注册监听器
    }

    // 定义状态变更监听器
    private StateMachineListener<OrderStatus, OrderEvent> listener() {
        return new StateMachineListenerAdapter<OrderStatus, OrderEvent>() {
            @Override
            public void transition(Transition<OrderStatus, OrderEvent> transition) {
                if (transition.getSource() != null && transition.getTarget() != null) {
                    System.out.println("状态变更：" + transition.getSource().getId()
                            + " -> " + transition.getTarget().getId()
                            + "，触发事件：" + transition.getTrigger().getEvent());
                }
            }
        };
    }



}
