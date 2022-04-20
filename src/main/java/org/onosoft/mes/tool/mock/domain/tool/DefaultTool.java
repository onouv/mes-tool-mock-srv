package org.onosoft.mes.tool.mock.domain.tool;

import org.onosoft.ddd.annotations.AggregateRoot;
import org.onosoft.mes.tool.mock.adapters.in.web.service.ToolRepositoryDefault;
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
import org.onosoft.mes.tool.mock.domain.tool.state.action.*;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.FlowIsFreeGuard;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.InportEmptyGuard;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.InportNotFullGuard;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.OutportFullGuard;
import org.onosoft.mes.tool.mock.domain.value.DomainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.state.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

    protected StateMachine<ToolStates, ToolEvents> stateMachine;
    protected ToolRepository toolRepository;

    protected DefaultTool(
        ToolId id,
        String name,
        String description,
        LoadPort inport,
        LoadPort outport,
        ToolRepository toolRepository) throws Exception {

        this.id = id;
        this.name = name;
        this.description = description;
        this.inport = inport;
        this.outport = outport;
        this.process = new Process();
        this.toolRepository = toolRepository;

        this.stateMachine = this.buildStateMachine();
        StateVarUtil.setToolId(this.stateMachine, this.id);
        this.stateMachine.start();
    }

    protected StateMachine<ToolStates, ToolEvents> buildStateMachine() throws Exception {

        StateMachineBuilder.Builder<ToolStates, ToolEvents> builder = StateMachineBuilder.builder();

        builder.configureStates().withStates()
            .initial(ToolStates.UP)
                .state(ToolStates.UP, new ToolUpEventAction(), null)
                .state(ToolStates.DOWN)
                .and()
            .withStates()
                .parent(ToolStates.UP)
                .initial(ToolStates.STOPPED)
                .state(ToolStates.STOPPED,new ToolStoppedEventAction(), null )
                .state(ToolStates.IDLE)
                .state(
                    ToolStates.PROCESSING,
                    new ToolBeginProcessingPartAction(),
                    new ToolDoneProcessingPartAction());

        builder.configureTransitions()
            .withExternal()
                .source(ToolStates.UP)
                .target(ToolStates.DOWN)
                .event(ToolEvents.FAULT)
                .and()
            .withExternal()
                .source(ToolStates.DOWN)
                .target(ToolStates.UP)
                .event(ToolEvents.FAULT_CLEARED)
                .and()
            .withExternal()
                .source(ToolStates.STOPPED)
                .target(ToolStates.IDLE)
                .event(ToolEvents.START)
                .guard(new InportEmptyGuard())
                .action(new ToolIdleEventUpstreamAction())
                .and()
            .withExternal()
                .source(ToolStates.STOPPED)
                .target(ToolStates.IDLE)
                .event(ToolEvents.START)
                .guard(new OutportFullGuard())
                .action(new ToolIdleEventDownStreamAction())
                .and()
            .withExternal()
                .source(ToolStates.STOPPED)
                .target(ToolStates.PROCESSING)
                .event(ToolEvents.START)
                .guard(new FlowIsFreeGuard())
                .and()
            .withExternal()
                .source(ToolStates.IDLE)
                .target(ToolStates.STOPPED)
                .event(ToolEvents.STOP)
                .and()
            .withExternal()
                .source(ToolStates.IDLE)
                .target(ToolStates.PROCESSING)
                .guard(new FlowIsFreeGuard())
                .and()
            .withExternal()
                .source(ToolStates.PROCESSING)
                .target(ToolStates.IDLE)
                .event(ToolEvents.FINISHED)
                .guard(new InportEmptyGuard())
                .action(new ToolIdleEventUpstreamAction())
                .and()
            .withExternal()
                .source(ToolStates.PROCESSING)
                .target(ToolStates.IDLE)
                .event(ToolEvents.FINISHED)
                .guard(new OutportFullGuard())
                .action(new ToolIdleEventDownStreamAction())
                .and()
            .withInternal()
                .source(ToolStates.UP)
                .event(ToolEvents.PART_LOADING)
                //.guard(new InportNotFullGuard())
                .action(new LoadPartAction())
                .and()
            .withInternal()
                .source(ToolStates.UP)
                .event(ToolEvents.PART_UNLOADING)
                //.guard(new OutportNotEmptyGuard())
                .action(new UnloadPartAction())
                .and()
            .withInternal()
                .source(ToolStates.PROCESSING)
                .timer(DefaultTool.CYCLE_TIME)
                .guard(new InportNotFullGuard())
                .action(new LoadPartAction());

        return builder.build();
    }

    public static Tool prototype(
        ToolId id,
        ToolDefinition definition,
        ToolRepository repo)

        throws Exception
    {

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
        return new DefaultTool(
            id,
            definition.getName(),
            definition.getDescription(),
            inport,
            outport,
            repo);
    }

    @Override
    public DomainResult create() throws ToolPreExistingException {

        if(this.toolRepository.findTool(this.id) != null)
            throw new ToolPreExistingException(id);

        this.toolRepository.insertTool(this);

        ToolCreatedEvent event = new ToolCreatedEvent(id);
        List<DomainEvent> events = new ArrayList<>();
        events.add(event);

        List<DomainEvent> fsmEvents = StateVarUtil.getDomainEvents(this.stateMachine);
        events.addAll(fsmEvents);

        return DomainResult.builder()
            .tool(this)
            .events(events)
            .build();
    }

    public DomainResult delete() {
        ToolDeletedEvent event = new ToolDeletedEvent(this.id);
        List<DomainEvent> events = new ArrayList<>();

        return DomainResult.builder()
            .tool(this)
            .events(events)
            .build();
    }
    @Override
    public DomainResult start() {
        this.stateMachine.sendEvent(ToolEvents.START);
        return this.domainResult();
    }

    @Override
    public DomainResult stop() {
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
    public DomainResult unloadPart(LoadportId port) {
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
        Part partInProc = this.process.getProcessedPart();
        if(partInProc != null)
            partsInProcess.add(partInProc.getId());
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
    public List<ToolStates> getStates() {
        List<ToolStates> result = new ArrayList<>();
        Collection<State<ToolStates, ToolEvents>> states = this.stateMachine.getState().getStates();
        for (State<ToolStates, ToolEvents> candidate : states) {
            if (!candidate.isSimple()) {
                Collection<ToolStates> ids = candidate.getIds();
                result.addAll(ids);
            }
        }
        return result;
    }

    protected DomainResult domainResult() {
        List<DomainEvent> events = StateVarUtil.getDomainEvents(this.stateMachine);
        ApplicationException exception = StateVarUtil.getApplicationException(this.stateMachine);
        State<ToolStates, ToolEvents> toolState = this.stateMachine.getState();

        return DomainResult.builder()
            .tool(this)
            .events(events)
            .applicationException(exception)
            .build();
    }
}
