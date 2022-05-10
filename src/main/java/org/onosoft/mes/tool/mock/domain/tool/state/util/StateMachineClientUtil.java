package org.onosoft.mes.tool.mock.domain.tool.state.util;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.required.DomainEventPublisher;
import org.onosoft.mes.tool.mock.domain.tool.ToolDefault;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.action.*;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.FlowIsFreeGuard;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.InportEmptyGuard;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.OutportFullGuard;
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
            .state(
                ToolStates.UP_PROCESSING,
                new ProcessNewPartAction(),
                null);

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
            .target(ToolStates.UP_PROCESSING)
            .event(ToolEvents.FINISHED)
            .guard(new FlowIsFreeGuard())
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
            .source(ToolStates.UP_PROCESSING)
            .target(ToolStates.UP_IDLE)
            .guard(new InportEmptyGuard())
            .action(new ToolIdleEventUpstreamAction())
            .and()
          .withInternal()
            .source(ToolStates.UP_PROCESSING)
            .timer(ToolDefault.CYCLE_TIME)
            .action(new CycleTimeElapsedAction());

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

        // TODO: this results in error (skipping of DOWN) - maybe need to switch/case on the actual ids ?
        if (!candidate.isSimple()) {
          Collection<ToolStates> ids = candidate.getIds();
          result.addAll(ids);
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
