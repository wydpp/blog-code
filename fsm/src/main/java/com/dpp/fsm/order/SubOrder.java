package com.dpp.fsm.order;

import com.dpp.fsm.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dpp
 * @date 2024/10/16
 * @Description
 */
public class SubOrder {

    public StateMachine newStateMachine() {
        StateMachine stateMachine = new StateMachine(new DefaultEventProcessor(), buildStateGraph());
        return stateMachine;
    }

    private StateGraph buildStateGraph() {
        StateGraph stateGraph = new StateGraph();
        stateGraph.setName("子订单状态机图表");
        stateGraph.setStart(State.StateWaitPay);
        stateGraph.setEnd(State.StatePayied);
        List<State> states = new ArrayList<>();
        states.add(State.StateWaitPay);
        states.add(State.StateCancel);
        states.add(State.StateWaitConfirm);
        states.add(State.StatePayied);
        stateGraph.setStates(states);
        stateGraph.setTransitions(buildTransitions());
        return stateGraph;
    }

    private List<Transition> buildTransitions() {
        List<Transition> transitions = new ArrayList<>();
        //取消：待支付--》已取消
        transitions.add(new Transition(State.StateWaitPay, Event.EventCancel, State.StateCancel,
                new DefaultLogAction(), null));
        //支付：待支付-》待确认
        transitions.add(new Transition(State.StateWaitPay, Event.EventPay, State.StateWaitConfirm,
                new DefaultLogAction(), null));
        //支付确认:待支付-》已支付
        transitions.add(new Transition(State.StateWaitPay, Event.EventPayConfirm, State.StatePayied,
                new DefaultLogAction(), null));
        //支付确认:待确认-》已支付
        transitions.add(new Transition(State.StateWaitConfirm, Event.EventPayConfirm, State.StatePayied,
                new DefaultLogAction(), null));
        return transitions;
    }
}
