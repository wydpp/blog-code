package com.dpp.fsm;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description 转变器
 */
public class Transition {

    private State from;

    private Event event;

    private State to;

    private Action action;

    private EventProcessor processor;

    public Transition(State from, Event event, State to, Action action, EventProcessor processor) {
        this.from = from;
        this.event = event;
        this.to = to;
        this.action = action;
        this.processor = processor;
    }

    public State getFrom() {
        return from;
    }

    public Event getEvent() {
        return event;
    }

    public State getTo() {
        return to;
    }

    public Action getAction() {
        return action;
    }

    public EventProcessor getProcessor() {
        return processor;
    }
}
