package org.onosoft.mes.tool.mock.domain.tool;

import org.onosoft.ddd.annotations.AggregateRoot;
import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
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
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.value.DomainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;

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

    protected DomainResult domainResult() {
        List<DomainEvent> events = StateVarUtil.getDomainEvents(this.stateMachine);
        ApplicationException exception = StateVarUtil.getApplicationException(this.stateMachine);
        State<ToolStates, ToolEvents> toolState = this.stateMachine.getState();

        DomainResult result = new DomainResult.DomainResultBuilder()
            .withToolId(id)
            .withToolState(toolState)
            .withDomainEvents(events)
            .withApplicationException(exception)
            .build();
        return result;
    }

    public ToolId getId() {
        return this.id;
    }


    @Override
    public DomainResult start() {
        this.stateMachine.sendEvent(ToolEvents.START);
        return this.domainResult();
    }

    @Override
    public DomainResult stop(IdleReason reason) {
        this.stateMachine.sendEvent(ToolEvents.STOP);
        return this.domainResult();
    }

    @Override
    public DomainResult loadPart(Part part, LoadportId portId) {
        StateVarUtil.setToolId(this.stateMachine, this.id);
        StateVarUtil.setPart(this.stateMachine, part);

        // TODO: lookup requested port by id, verify it's an inport
        StateVarUtil.setInport(this.stateMachine, this.inport);
        this.stateMachine.sendEvent(ToolEvents.PART_LOADING);
        return this.domainResult();
    }

    @Override
    public DomainResult unloadPart(LoadportId port) throws NoPartAvailableException {
        StateVarUtil.setToolId(this.stateMachine, this.id);
        // TODO: lookup requested port by id, verify it's an outport
        StateVarUtil.setOutport(this.stateMachine, this.outport);
        this.stateMachine.sendEvent(ToolEvents.PART_UNLOADING);
        return this.domainResult();
    }

    @Override
    public DomainResult breakDown() {
        this.stateMachine.sendEvent(ToolEvents.FAULT);
        return this.domainResult();
    }

    @Override
    public DomainResult repair() {
        this.stateMachine.sendEvent(ToolEvents.FAULT_CLEARED);
        return this.domainResult();
    }

}
