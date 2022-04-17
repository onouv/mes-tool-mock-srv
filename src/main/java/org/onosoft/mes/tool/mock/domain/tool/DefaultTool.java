package org.onosoft.mes.tool.mock.domain.tool;

import org.onosoft.ddd.annotations.AggregateRoot;
import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.ToolCreatedEvent;
import org.onosoft.mes.tool.mock.domain.event.ToolDeletedEvent;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.ToolPreExistingException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.*;
import org.onosoft.mes.tool.mock.domain.required.ToolRepository;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.entity.Process;
import org.onosoft.mes.tool.mock.domain.tool.state.StateVarUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.value.DomainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;

import java.util.ArrayList;
import java.util.List;

@AggregateRoot
public class DefaultTool implements Tool {

    public static final int CYCLE_TIME = 2000;  // ms
    protected ToolId id;
    protected String name;
    protected String description;

    protected final LoadPort outport;
    protected final LoadPort inport;
    protected final Process process;

    @Autowired
    StateMachine<ToolStates, ToolEvents> stateMachine;

    @Autowired
    ToolRepository toolRepository;

    protected DefaultTool(
        ToolId id,
        String name,
        String description,
        LoadPort inport,
        LoadPort outport) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.inport = inport;
        this.outport = outport;
        this.process = new Process();
    }


    public static Tool prototype(ToolId id, ToolDefinition definition) {

        LoadPort inport = new LoadPort(
            id,
            definition.getInport().getId(),
            definition.getInport().getCapacity()
        );
        LoadPort outport = new LoadPort(
            id,
            definition.getOutport().getId(),
            definition.getOutport().getCapacity()
        );
        DefaultTool tool = new DefaultTool(
            id,
            definition.getName(),
            definition.getDescription(),
            inport,
            outport);
        return tool;
    }

    @Override
    public DomainResult create() throws ToolPreExistingException {

        if(this.toolRepository.findTool(this.id) != null)
            throw new ToolPreExistingException(id);

        this.toolRepository.insertTool(this);

        ToolCreatedEvent event = new ToolCreatedEvent(id);
        List<DomainEvent> events = new ArrayList<DomainEvent>();
        events.add(event);

        List<DomainEvent> fsmEvents = StateVarUtil.getDomainEvents(this.stateMachine);
        events.addAll(fsmEvents);

        DomainResult result = DomainResult.builder()
            .tool(this)
            .events(events)
            .build();

        return result;
    }

    public DomainResult delete() {
        ToolDeletedEvent event = new ToolDeletedEvent(this.id);
        List<DomainEvent> events = new ArrayList<DomainEvent>();
        DomainResult result = DomainResult.builder()
            .tool(this)
            .events(events)
            .build();

        return result;
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
    @Override
    public ToolId getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<PartId> getPartsInProcess() {
        List<PartId> partsInProcess = new ArrayList<>();
        partsInProcess.add(this.process.getProcessedPart().getId());
        return partsInProcess;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public LoadPort getInport() {
        return this.inport;
    }

    public LoadPort getOutport() {
        return this.outport;
    }

    @Override
    public ToolStates getStatus() {
        return this.stateMachine.getState().getId();
    }
    protected DomainResult domainResult() {
        List<DomainEvent> events = StateVarUtil.getDomainEvents(this.stateMachine);
        ApplicationException exception = StateVarUtil.getApplicationException(this.stateMachine);
        State<ToolStates, ToolEvents> toolState = this.stateMachine.getState();

        DomainResult result = DomainResult.builder()
            .tool(this)
            .events(events)
            .applicationException(exception)
            .build();
        return result;
    }
}
