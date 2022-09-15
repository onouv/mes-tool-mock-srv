package org.onosoft.mes.tool.mock.domain.tool;

import lombok.AccessLevel;
import lombok.Getter;
import org.onosoft.ddd.annotations.AggregateRoot;
import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.ToolCreatedEvent;
import org.onosoft.mes.tool.mock.domain.event.ToolDeletedEvent;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.exception.IllegalLoadportTypeException;
import org.onosoft.mes.tool.mock.domain.exception.ToolPreExistingException;
import org.onosoft.mes.tool.mock.domain.required.DomainEventPublisher;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.*;
import org.onosoft.mes.tool.mock.domain.required.ToolRepository;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.entity.Process;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateMachineUtil;
import org.onosoft.mes.tool.mock.domain.value.DomainResult;

import java.util.ArrayList;
import java.util.List;

@AggregateRoot
@Getter
public class ToolDefault implements Tool {

    public static final int CYCLE_TIME = 2000;  // ms
    protected ToolId id;
    protected String name;
    protected String description;

    protected final LoadPort outport;
    protected final LoadPort inport;
    protected final Process process;

    @Getter(AccessLevel.NONE)
    protected StateMachineUtil state;
    @Getter(AccessLevel.NONE)
    protected ToolRepository toolRepository;

    protected ToolDefault(
        ToolId id,
        String name,
        String description,
        LoadPort inport,
        LoadPort outport,
        DomainEventPublisher partDomainEventPublisher
        ) throws Exception {

        this.id = id;
        this.name = name;
        this.description = description;
        this.inport = inport;
        this.outport = outport;
        this.process = new Process();
        this.state = new StateMachineUtil(this, partDomainEventPublisher);
    }

    public static Tool prototype(
        ToolId id,
        ToolDefinition definition,
        DomainEventPublisher publisher) throws Exception {

        LoadPort inport = new LoadPort(
            id,
            definition.getInport().getId(),
            LoadportType.INPORT,
            definition.getInport().getCapacity()
        );
        LoadPort outport = new LoadPort(
            id,
            definition.getOutport().getId(),
            LoadportType.OUTPORT,
            definition.getOutport().getCapacity()
        );
        return new ToolDefault(
            id,
            definition.getName(),
            definition.getDescription(),
            inport,
            outport,
            publisher);
    }

    @Override
    public DomainResult create() throws ToolPreExistingException {
        ToolCreatedEvent createdEvent = new ToolCreatedEvent(id);
        List<DomainEvent> events = new ArrayList<>();
        events.add(createdEvent);

        this.state.start();
        events.addAll(this.state.getDomainEvents());
        this.state.clearVariableFields();

        return DomainResult.builder()
            .tool(this)
            .events(events)
            .build();
    }

    public DomainResult delete() {
        this.state.send(ToolEvents.STOP);
        DomainResult result = this.domainResult();

        ToolDeletedEvent deletedEvent = new ToolDeletedEvent(this.id);
        result.getEvents().add(deletedEvent);
        return result;
    }

    @Override
    public DomainResult start() {
        this.state.send(ToolEvents.START);
        return this.domainResult();
    }

    @Override
    public DomainResult stop() {
        this.state.send(ToolEvents.STOP);
        return this.domainResult();
    }

    @Override
    public DomainResult loadPart(Part part, LoadportId portId)
        throws IllegalLoadportTypeException {

        if( ! this.inport.getId().equals(portId))
            throw new IllegalLoadportTypeException(this.id, portId);

        this.state.setPart(part);

        this.state.send(ToolEvents.PART_LOADING);
        return this.domainResult();
    }

    @Override
    public DomainResult unloadPart(LoadportId portId)
        throws IllegalLoadportTypeException {

        if( ! this.outport.getId().equals(portId))
            throw new IllegalLoadportTypeException(this.id, portId);



        this.state.send(ToolEvents.PART_UNLOADING);
        return this.domainResult();
    }

    @Override
    public DomainResult fault() {
        this.state.send(ToolEvents.FAULT);
        return this.domainResult();
    }

    @Override
    public DomainResult clearFault() {
        this.state.send(ToolEvents.FAULT_CLEARED);
        return this.domainResult();
    }

    @Override
    public List<PartId> getPartsInProcess() {
        List<PartId> partsInProcess = new ArrayList<>();
        Part partInProc = this.process.getProcessingPart();
        if(partInProc != null)
            partsInProcess.add(partInProc.getId());
        return partsInProcess;
    }

    @Override
    public List<ToolStates> getCurrentStates() {
        return this.state.getCurrentStates();
    }

    protected DomainResult domainResult() {
        List<DomainEvent> events = this.state.getDomainEvents();
        ApplicationException exception = this.state.getException();
        this.state.clearVariableFields();

        return DomainResult.builder()
            .tool(this)
            .events(events)
            .applicationException(exception)
            .build();
    }

}
