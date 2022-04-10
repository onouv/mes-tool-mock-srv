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
import org.onosoft.mes.tool.mock.domain.tool.state.StateVarUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AggregateRoot
public class DefaultTool implements Tool {

    public static final int CYCLE_TIME = 2000;  // ms

    protected Identifier id;
    protected static final int WIP_CAPACITY_DEFAULT = 11;
    protected static final int WIP_PROCESS = 1;

    protected final LoadPort outport;
    protected final LoadPort inport;
    protected final Process process;

    @Autowired
    StateMachine<ToolStates, ToolEvents> stateMachine;

    public DefaultTool(Identifier id) {
        this.id = id;
        this.inport = new LoadPort(
            new ToolId("in.1"),
            WIP_CAPACITY_DEFAULT - WIP_PROCESS);
        this.outport = new LoadPort(
            new ToolId("out.1"),
            WIP_CAPACITY_DEFAULT - WIP_PROCESS);
        this.process = new Process();
    }

    public DefaultTool(Identifier id, int wipCapacity) {
        this.id = id;
        if (wipCapacity < 0) {
            throw new IllegalArgumentException("wipCapacity must be positive number.");
        }
        this.inport = new LoadPort(new ToolId("in.1"), wipCapacity - WIP_PROCESS);
        this.outport = new LoadPort(new ToolId("out.1"), wipCapacity - WIP_PROCESS);
        this.process = new Process();

    }

    protected EventBundle domainResult() {
        List<DomainEvent> events = (List<DomainEvent>) StateVarUtil.getDomainEvents(this.stateMachine);
        EventBundle bundle = new EventBundle(this, events);
        return bundle;
    }

    public Identifier getId() {
        return this.id;
    }


    @Override
    public EventBundle start() {

        this.stateMachine.sendEvent(ToolEvents.START);
        return this.domainResult();

    }

    @Override
    public EventBundle stop(IdleReason reason) {
        this.stateMachine.sendEvent(ToolEvents.STOP);
        return this.domainResult();
    }

    @Override
    public EventBundle loadPart(Part part) throws LoadportFullException {
        StateVarUtil.setPart(this.stateMachine, part);

        this.stateMachine.sendEvent(ToolEvents.PART_LOADING);

        return this.domainResult();
    }

    @Override
    public EventBundle unloadPart() throws NoPartAvailableException {

        this.stateMachine.sendEvent(ToolEvents.PART_UNLOADING);

        return this.domainResult();
    }

    @Override
    public EventBundle breakDown() {

        return this.domainResult();
    }

    @Override
    public EventBundle repair() {

        return this.domainResult();
    }

}
