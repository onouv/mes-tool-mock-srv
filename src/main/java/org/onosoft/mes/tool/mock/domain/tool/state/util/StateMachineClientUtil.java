package org.onosoft.mes.tool.mock.domain.tool.state.util;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.required.DomainEventPublisher;
import org.onosoft.mes.tool.mock.domain.tool.ToolDefault;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.action.*;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.*;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.state.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class StateMachineClientUtil {
    StateMachine<ToolStates, ToolEvents> stateMachine;
    Map<Object, Object> stateVariables;
    ToolDefault tool;

    public StateMachineClientUtil(ToolDefault tool, DomainEventPublisher publisher) throws Exception {
      this.tool = tool;
      this.stateMachine = this.buildStateMachine();
      this.stateVariables = this.stateMachine.getExtendedState().getVariables();
      this.stateVariables.put(StateVariableKeys.toolId, this.tool.getId());
      this.stateVariables.put(StateVariableKeys.inport, this.tool.getInport());
      this.stateVariables.put(StateVariableKeys.outport, this.tool.getOutport());
      this.stateVariables.put(StateVariableKeys.process, this.tool.getProcess());
      this.stateVariables.put(StateVariableKeys.partDomainEventPublisher, publisher);
      this.stateMachine.start();
    }

    protected StateMachine<ToolStates, ToolEvents> buildStateMachine() throws Exception {

      StateMachineBuilder.Builder<ToolStates, ToolEvents> builder = StateMachineBuilder.builder();

      builder.configureStates()
          .withStates()
            .initial(ToolStates.UP)
            .stateEntry(ToolStates.UP, new ToolUpEventAction())
            .and()
            .withStates()
              .parent(ToolStates.UP)
              .initial(ToolStates.STOPPED)
              .stateEntry(ToolStates.STOPPED, new ToolStoppedEventAction())
              .state(ToolStates.IDLE)
              .state(ToolStates.PROCESSING)
              .and()
              .withStates()
                .parent(ToolStates.PROCESSING)
                .initial(ToolStates.PROCESSING_PART, new ProcessNewPartAction())
                .state(ToolStates.PROCESSING_PART)
                .state(ToolStates.UNLOADING_PROCESS)
                .state(ToolStates.LOADING_PROCESS)
                .and()
          .withStates()
            .stateEntry(ToolStates.DOWN, new ToolDownEventAction());

      builder.configureTransitions()
          .withExternal()
            .source(ToolStates.UP)
            .target(ToolStates.DOWN)
            .event(ToolEvents.FAULT)
            .and()
          .withInternal()
            .source(ToolStates.UP)
            .event(ToolEvents.PART_LOADING)
            .action(new LoadPartAction())
            .and()
          .withInternal()
            .source(ToolStates.UP)
            .event(ToolEvents.PART_UNLOADING)
            .action(new UnloadPartAction())
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
            .action(new ProcessNewPartAction())
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
          .withExternal()
            .source(ToolStates.PROCESSING)
            .target(ToolStates.STOPPED)
            .event(ToolEvents.STOP)
            .and()
          .withExternal()
            .source(ToolStates.PROCESSING_PART)
            .target(ToolStates.LOADING_PROCESS)
            .guard(new ProcessEmptyGuard())
            .and()
          .withExternal()
            .source(ToolStates.LOADING_PROCESS)
            .target(ToolStates.PROCESSING_PART)
            .and()
          .withExternal()
            .source(ToolStates.PROCESSING_PART)
            .target(ToolStates.UNLOADING_PROCESS)
            .timerOnce(ToolDefault.CYCLE_TIME)
            .action(new EjectFinishedPartAction())
            .and()
          .withExternal()
            .source(ToolStates.UNLOADING_PROCESS)
            .target(ToolStates.LOADING_PROCESS)
            .guard(new FlowIsFreeGuard())
            .and()
          ;

      builder.configureConfiguration()
          .withConfiguration()
          .listener(new StateMachineListener());

      return builder.build();
    }

    public void send(ToolEvents event) {
      this.stateMachine.sendEvent(event);
    }

    public List<ToolStates> getCurrentStates() {
      List<ToolStates> result = new ArrayList<>();
      Collection<State<ToolStates, ToolEvents>> states =
          this.stateMachine.getState().getStates();
      for (State<ToolStates, ToolEvents> candidate : states) {
        ToolStates current = candidate.getId();
        switch(current) {
          case DOWN:
            result.add(current);
            break;
          case UP:
            result.addAll(candidate.getIds());
            break;
        }
      }
      return result;
    }

    public List<DomainEvent> getDomainEvents() {
      return (List<DomainEvent>) this.stateVariables.get(StateVariableKeys.domainEvents);
    }

    public ApplicationException getException() {
      return (ApplicationException) this.stateVariables.get(StateVariableKeys.exception);
    }

    public void setPart(Part p) {
      this.stateVariables.put(StateVariableKeys.part, p);
    }

    public Part getPart() {
      return (Part) this.stateVariables.get(StateVariableKeys.part);
    }

    public void clearVariableFields() {
      this.stateVariables.replace(StateVariableKeys.domainEvents, new ArrayList<DomainEvent>());
      this.stateVariables.remove(StateVariableKeys.part);
      this.stateVariables.remove(StateVariableKeys.exception);
    }
}
