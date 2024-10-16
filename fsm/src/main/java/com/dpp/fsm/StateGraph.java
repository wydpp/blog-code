package com.dpp.fsm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description 状态机图表
 */
public class StateGraph {
    /**
     * 图标名称
     */
    private String name;
    /**
     * 起始状态
     */
    private State start;
    /**
     * 结束状态
     */
    private State end;
    /**
     * 状态集合
     */
    private List<State> states;
    /**
     * 转变器集合
     */
    private List<Transition> transitions;

    private Map<State, Map<Event, Transition>> transitionMap = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getStart() {
        return start;
    }

    public void setStart(State start) {
        this.start = start;
    }

    public State getEnd() {
        return end;
    }

    public void setEnd(State end) {
        this.end = end;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
        if (transitions != null) {
            transitionMap.clear();
            for (Transition transition : transitions) {
                Map<Event, Transition> map = transitionMap.computeIfAbsent(transition.getFrom(), k -> new HashMap<>());
                map.put(transition.getEvent(),transition);
            }
        }
    }

    public Transition getTransition(State state, Event event) {
        Map<Event, Transition> eventTransitionMap = transitionMap.get(state);
        if (eventTransitionMap != null){
            return eventTransitionMap.get(event);
        }
        return null;
    }
}


