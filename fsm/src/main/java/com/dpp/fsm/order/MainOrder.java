package com.dpp.fsm.order;

import com.dpp.fsm.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description
 */
public class MainOrder {

    public MainOrder() {
    }

    public StateMachine newStateMachine() {
        StateMachine stateMachine = new StateMachine(new DefaultEventProcessor(), buildStateGraph());
        return stateMachine;
    }

    private StateGraph buildStateGraph() {
        StateGraph stateGraph = new StateGraph();
        stateGraph.setName("主订单状态机图表");
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
        transitions.add(new Transition(State.StateWaitPay, Event.EventCancel, State.StateCancel,
                new DefaultLogAction(), new DefaultEventProcessor()));
        transitions.add(new Transition(State.StateWaitPay, Event.EventCancel, State.StateCancel,
                new DefaultLogAction(), new DefaultEventProcessor()));
        transitions.add(new Transition(State.StateWaitPay, Event.EventCancel, State.StateCancel,
                new DefaultLogAction(), new DefaultEventProcessor()));
        return transitions;
    }
}
