package org.onosoft.mes.tool.mock.domain.tool;

import org.onosoft.ddd.annotations.AggregateRoot;
import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.EventBundle;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.entity.Process;
import org.onosoft.mes.tool.mock.domain.tool.state.StateVarUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolStates;
import org.onosoft.mes.tool.mock.domain.value.DomainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;

import java.util.List;

@AggregateRoot
public class DefaultTool implements Tool {

    public static final int CYCLE_TIME = 2000;  // ms

    protected ToolId id;
    protected static final int WIP_CAPACITY_DEFAULT = 11;
    protected static final int WIP_PROCESS = 1;

    protected final LoadPort outport;
    protected final LoadPort inport;
    protected final Process process;

    @Autowired
    StateMachine<ToolStates, ToolEvents> stateMachine;

    public DefaultTool(ToolId id) {
        this.id = id;
        this.inport = new LoadPort(
            this.id,
            new LoadportId("in.1"),
            WIP_CAPACITY_DEFAULT - WIP_PROCESS);
        this.outport = new LoadPort(
            this.id,
            new LoadportId("out.1"),
            WIP_CAPACITY_DEFAULT - WIP_PROCESS);
        this.process = new Process();
    }

    public DefaultTool(ToolId id, int wipCapacity) {
        this.id = id;
        if (wipCapacity < 0) {
            throw new IllegalArgumentException("wipCapacity must be positive number.");
        }
        this.inport = new LoadPort(this.id, new LoadportId("in.1"), wipCapacity - WIP_PROCESS);
        this.outport = new LoadPort(this.id, new LoadportId("out.1"), wipCapacity - WIP_PROCESS);
        this.process = new Process();

    }

    protected EventBundle domainResult() {
        DomainResult domainResult = StateVarUtil.getDomainResult(this.stateMachine);
        List<DomainEvent> events = domainResult.getEvents();
        ApplicationException exception = domainResult.getApplicationException();
        EventBundle bundle = new EventBundle(this, events, exception);
        return bundle;
    }

    public ToolId getId() {
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
    public EventBundle loadPart(Part part) {
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
        this.stateMachine.sendEvent(ToolEvents.FAULT);
        return this.domainResult();
    }

    @Override
    public EventBundle repair() {
        this.stateMachine.sendEvent(ToolEvents.FAULT_CLEARED);
        return this.domainResult();
    }

}
