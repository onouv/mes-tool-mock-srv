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
            .state(ToolStates.UP, new ToolUpEventAction(), null)
            .state(ToolStates.DOWN)
            .and()
          .withStates()
            .parent(ToolStates.UP)
            .initial(ToolStates.UP_STOPPED)
            .state(ToolStates.UP_STOPPED, new ToolStoppedEventAction(), null)
            .state(ToolStates.UP_IDLE)
            .state(ToolStates.UP_PROCESSING)
            .and()
          .withStates()
            .parent(ToolStates.UP_PROCESSING)
            .initial(ToolStates.UP_PROCESSING_PROCESSING_PART)
            .state(ToolStates.UP_PROCESSING_LOADING_PART)
            .stateExit(ToolStates.UP_PROCESSING_PROCESSING_PART, new EjectFinishedPartAction())
            .state(ToolStates.UP_PROCESSING_UNLOADING_PART);

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
            .source(ToolStates.UP_STOPPED)
            .target(ToolStates.UP_IDLE)
            .event(ToolEvents.START)
            .guard(new InportEmptyGuard())
            .action(new ToolIdleEventUpstreamAction())
            .and()
          .withExternal()
            .source(ToolStates.UP_STOPPED)
            .target(ToolStates.UP_IDLE)
            .event(ToolEvents.START)
            .guard(new OutportFullGuard())
            .action(new ToolIdleEventDownStreamAction())
            .and()
          .withExternal()
            .source(ToolStates.UP_STOPPED)
            .target(ToolStates.UP_PROCESSING)
            .event(ToolEvents.START)
            .guard(new FlowIsFreeGuard())
            .and()
          .withExternal()
            .source(ToolStates.UP_IDLE)
            .target(ToolStates.UP_STOPPED)
            .event(ToolEvents.STOP)
            .and()
          .withExternal()
            .source(ToolStates.UP_IDLE)
            .target(ToolStates.UP_PROCESSING)
            .guard(new FlowIsFreeGuard())
            .and()
          .withExternal()
            .source(ToolStates.UP_PROCESSING)
            .target(ToolStates.UP_IDLE)
            .event(ToolEvents.FINISHED)
            .guard(new InportEmptyGuard())
            .action(new ToolIdleEventUpstreamAction())
            .and()
          .withExternal()
            .source(ToolStates.UP_PROCESSING)
            .target(ToolStates.UP_IDLE)
            .event(ToolEvents.FINISHED)
            .guard(new OutportFullGuard())
            .action(new ToolIdleEventDownStreamAction())
            .and()
          .withExternal()
            .source(ToolStates.UP_PROCESSING)
            .target(ToolStates.UP_STOPPED)
            .event(ToolEvents.STOP)
            .and()
          .withExternal()
            .source(ToolStates.UP_PROCESSING_PROCESSING_PART)
            .target(ToolStates.UP_PROCESSING_LOADING_PART)
            .guard(new ProcessEmptyGuard())
            .and()
          .withExternal()
            .source(ToolStates.UP_PROCESSING_LOADING_PART)
            .target(ToolStates.UP_PROCESSING_PROCESSING_PART)
            .action(new ProcessNewPartAction())
            .and()
          .withExternal()
            .source(ToolStates.UP_PROCESSING_PROCESSING_PART)
            .target(ToolStates.UP_PROCESSING_UNLOADING_PART)
            .timer(ToolDefault.CYCLE_TIME)
            .action(new CycleTimeElapsedAction())
            .and()
          .withExternal()
            .source(ToolStates.UP_PROCESSING_UNLOADING_PART)
            .target(ToolStates.UP_PROCESSING_LOADING_PART)
            .guard(new FlowIsFreeGuard())
            .and()
          .withExternal()
            .source(ToolStates.UP_PROCESSING_UNLOADING_PART)
            .target(ToolStates.UP)
            .guard(new FlowIsNotFreeGuard());

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
