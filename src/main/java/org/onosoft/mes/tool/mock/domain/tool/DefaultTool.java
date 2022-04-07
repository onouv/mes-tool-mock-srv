package org.onosoft.mes.tool.mock.domain.tool;

import jdk.jfr.Event;
import org.onosoft.ddd.annotations.AggregateRoot;
import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.EventBundle;
import org.onosoft.mes.tool.mock.domain.event.PartUnloadedEvent;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;
import org.onosoft.mes.tool.mock.domain.provided.value.Identifier;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.entity.Process;
import org.onosoft.mes.tool.mock.domain.tool.state.StateVarKeys;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AggregateRoot
public class DefaultTool implements Tool {

    protected Identifier id;
    protected static final int WIP_CAPACITY_DEFAULT = 11;
    protected static final int WIP_PROCESS = 1;
    protected static final int CYCLE_TIME = 2000;  // ms

    protected final LoadPort outport;
    protected final LoadPort inport;
    protected final Process process;

    @Autowired
    StateMachine<ToolStates, ToolEvents> stateMachine;
    Map<Object, Object> extendedStateVars;

    public DefaultTool(Identifier id) {
        this.id = id;
        this.inport = new LoadPort(
            new ToolId("in.1"),
            WIP_CAPACITY_DEFAULT - WIP_PROCESS);
        this.outport = new LoadPort(
            new ToolId("out.1"),
            WIP_CAPACITY_DEFAULT - WIP_PROCESS);
        this.process = new Process();
        this.initStateMachine();
    }

    public DefaultTool(Identifier id, int wipCapacity) {
        this.id = id;
        if (wipCapacity < 0) {
            throw new IllegalArgumentException("wipCapacity must be positive number.");
        }
        this.inport = new LoadPort(new ToolId("in.1"), wipCapacity - WIP_PROCESS);
        this.outport = new LoadPort(new ToolId("out.1"), wipCapacity - WIP_PROCESS);
        this.process = new Process();
        this.initStateMachine();
    }

    protected void initStateMachine() {
        this.extendedStateVars = this.stateMachine.getExtendedState().getVariables();
        this.extendedStateVars.put(StateVarKeys.toolId, this.id);
        this.extendedStateVars.put(StateVarKeys.inport, this.inport);
        this.extendedStateVars.put(StateVarKeys.outport, this.outport);
        this.extendedStateVars.put(StateVarKeys.process, this.process;
        this.stateMachine.start();
    }

    public Identifier getId() {
        return this.id;
    }


    @Override
    public EventBundle start() {
        this.stateMachine.sendEvent(ToolEvents.START);
    }

    @Override
    public EventBundle stop(IdleReason reason) {
        this.stateMachine.sendEvent(ToolEvents.STOP);
    }

    @Override
    public EventBundle loadPart(Part part) throws LoadportFullException {
        this.extendedStateVars.put(StateVarKeys.part, part);
        this.stateMachine.sendEvent(ToolEvents.PART_LOADING);
        List<DomainEvent> events = (List<DomainEvent>) this.extendedStateVars.get(StateVarKeys.domainEvents);
        EventBundle bundle = new EventBundle(this, events);
        return bundle;
    }

    @Override
    public EventBundle unloadPart() throws NoPartAvailableException {

        this.stateMachine.sendEvent(ToolEvents.PART_UNLOADING);

        List<DomainEvent> events = (List<DomainEvent>) this.extendedStateVars.get(StateVarKeys.domainEvents);
        Part part = (Part) this.extendedStateVars.get(StateVarKeys.part);

        EventBundle bundle = new EventBundle(this, events);
        return bundle;
    }

    @Override
    public EventBundle breakDown() {

    }

    @Override
    public EventBundle repair() {

    }

}
