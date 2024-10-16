package com.dpp.fsm;

import com.dpp.fsm.error.OldStateDontHaveTheEventTransition;
import com.dpp.fsm.error.StateIsEndException;
import com.dpp.fsm.error.StateNotExistException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description 状态机
 */
public class StateMachine {
    /**
     * 锁
     */
    private Lock locker = new ReentrantLock();
    /**
     * 事件处理器
     */
    private EventProcessor processor;
    /**
     * 状态图表
     */
    private StateGraph graph;

    public StateMachine(EventProcessor processor, StateGraph graph) {
        this.processor = processor;
        this.graph = graph;
    }

    public State run(State from, Event event) throws StateNotExistException {
        System.out.printf("\n开始执行，旧状态为 %s,事件为 %s", from, event);
        //检测状态是否存在
        if (!graph.getStates().contains(from)) {
            throw new StateNotExistException();
        }
        //检测状态是否已达到最终状态
        if (from == graph.getEnd()) {
            throw new StateIsEndException();
        }
        //检测事件和状态是否匹配
        Transition transition = graph.getTransition(from, event);
        if (transition == null) {
            throw new OldStateDontHaveTheEventTransition();
        }
        //新状态
        State to = transition.getTo();
        locker.lock();
        try {
            //执行状态机处理器的退出旧状态方法
            this.processor.exitOldState(from, event);
            //如果转变器有处理器，则执行该处理器的退出旧状态方法
            if (transition.getProcessor() != null) {
                transition.getProcessor().exitOldState(from, event);
            }
            //执行转变器的动作
            ActionResult actionResult = transition.getAction().action(from, event, to);
            System.out.printf("\nactionResult = %s", actionResult);
            this.processor.enterNewState(to, event);
            if (transition.getProcessor() != null) {
                transition.getProcessor().enterNewState(to, event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
        System.out.println("\n执行结束！");
        return to;
    }
}
