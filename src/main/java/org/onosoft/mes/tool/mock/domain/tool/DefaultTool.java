package org.onosoft.mes.tool.mock.domain.tool;

import org.onosoft.ddd.annotations.AggregateRoot;
import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.ToolCreatedEvent;
import org.onosoft.mes.tool.mock.domain.event.ToolDeletedEvent;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.exception.IllegalLoadportTypeException;
import org.onosoft.mes.tool.mock.domain.exception.ToolPreExistingException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.*;
import org.onosoft.mes.tool.mock.domain.required.ToolRepository;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.entity.Process;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.action.*;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.FlowIsFreeGuard;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.InportEmptyGuard;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.InportNotFullGuard;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.OutportFullGuard;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateVariableKeys;
import org.onosoft.mes.tool.mock.domain.value.DomainResult;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@AggregateRoot
public class DefaultTool implements Tool {

    public static final int CYCLE_TIME = 2000;  // ms
    protected ToolId id;
    protected String name;
    protected String description;

    protected final LoadPort outport;
    protected final LoadPort inport;
    protected final Process process;

    protected static class State {
        StateMachine<ToolStates, ToolEvents> stateMachine;
        Map<Object, Object> stateVariables;
        DefaultTool tool;

        State(DefaultTool tool) throws Exception {
            this.tool = tool;
            this.stateMachine = this.buildStateMachine();
            this.stateVariables = this.stateMachine.getExtendedState().getVariables();
            this.stateVariables.put(StateVariableKeys.toolId, this.tool.id);
            this.stateVariables.put(StateVariableKeys.inport, this.tool.inport);
            this.stateVariables.put(StateVariableKeys.outport, this.tool.outport);
            this.stateVariables.put(StateVariableKeys.process, this.tool.process);
            this.stateMachine.start();
        }

        private StateMachine<ToolStates, ToolEvents> buildStateMachine() throws Exception {

            StateMachineBuilder.Builder<ToolStates, ToolEvents> builder = StateMachineBuilder.builder();

            builder.configureStates().withStates()
                .initial(ToolStates.UP)
                .state(ToolStates.UP, new ToolUpEventAction(), null)
                .state(ToolStates.DOWN)
                .and()
                .withStates()
                .parent(ToolStates.UP)
                .initial(ToolStates.STOPPED)
                .state(ToolStates.STOPPED, new ToolStoppedEventAction(), null)
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

        void send(ToolEvents event) {
            this.stateMachine.sendEvent(event);
        }

        List<ToolStates> getCurrentStates() {
            List<ToolStates> result = new ArrayList<>();
            Collection<org.springframework.statemachine.state.State<ToolStates, ToolEvents>> states = this.stateMachine.getState().getStates();
            for (org.springframework.statemachine.state.State<ToolStates, ToolEvents> candidate : states) {
                if (!candidate.isSimple()) {
                    Collection<ToolStates> ids = candidate.getIds();
                    result.addAll(ids);
                }
            }
            return result;
        }

        List<DomainEvent> getDomainEvents() {
            return (List<DomainEvent>) this.stateVariables.get(StateVariableKeys.domainEvents);
        }

        ApplicationException getException() {
            return (ApplicationException) this.stateVariables.get(StateVariableKeys.exception);
        }

        void setPart(Part p) {
            this.stateVariables.put(StateVariableKeys.part, p);
        }

        Part getPart() {
            return (Part) this.stateVariables.get(StateVariableKeys.part);
        }
        void clearVariableFields() {
            this.stateVariables.replace(StateVariableKeys.domainEvents, new ArrayList<DomainEvent>());
            this.stateVariables.remove(StateVariableKeys.part);
            this.stateVariables.remove(StateVariableKeys.exception);
        }
    }

    protected State state;
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
        this.state = new State(this);

        this.toolRepository = toolRepository;
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
            LoadportType.INPORT,
            definition.getInport().getCapacity()
        );
        LoadPort outport = new LoadPort(
            id,
            definition.getOutport().getId(),
            LoadportType.OUTPORT,
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

        List<DomainEvent> events = this.state.getDomainEvents();
        ToolCreatedEvent createdEvent = new ToolCreatedEvent(id);
        events.add(createdEvent);
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
