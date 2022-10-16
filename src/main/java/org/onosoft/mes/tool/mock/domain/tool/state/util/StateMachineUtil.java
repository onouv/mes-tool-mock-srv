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


public class StateMachineUtil {
    StateMachine<ToolStates, ToolEvents> stateMachine;
    Map<Object, Object> stateVariables;
    ToolDefault tool;

    public StateMachineUtil(ToolDefault tool, DomainEventPublisher publisher) throws Exception {
      this.tool = tool;
      this.stateMachine = this.buildStateMachine();
      this.stateVariables = this.stateMachine.getExtendedState().getVariables();
      this.stateVariables.put(StateVariableKeys.toolId, this.tool.getId());
      this.stateVariables.put(StateVariableKeys.inport, this.tool.getInport());
      this.stateVariables.put(StateVariableKeys.outport, this.tool.getOutport());
      this.stateVariables.put(StateVariableKeys.process, this.tool.getProcess());
      this.stateVariables.put(StateVariableKeys.partDomainEventPublisher, publisher);
    }

    protected StateMachine<ToolStates, ToolEvents> buildStateMachine() throws Exception {

      StateMachineBuilder.Builder<ToolStates, ToolEvents> builder = StateMachineBuilder.builder();

      builder.configureStates()
          .withStates()
              .initial(ToolStates.UP)
                  .stateEntry(ToolStates.UP, new ToolUpEventAction())
                      .stateEntry(ToolStates.DOWN, new ToolDownEventAction())
                          .and()

          .withStates()
              .parent(ToolStates.UP)
                  .stateEntry(ToolStates.STOPPED, new ToolStoppedEventAction())
                      .choice(ToolStates.UP_CHOICE)
                          .stateEntry(ToolStates.IDLE, new ToolIdleAction())
                              .stateEntry(ToolStates.OPERATING, new ToolOperatingAction())
                                  .initial(ToolStates.STOPPED)
                                      .and()

          .withStates()
              .parent(ToolStates.IDLE)
                  .state(ToolStates.IDLE_CHOICE)
                  .stateEntry(ToolStates.IDLE_UPSTREAM, new ToolIdleUpstreamEventAction())
                      .stateEntry(ToolStates.IDLE_DOWNSTREAM,new ToolIdleDownStreamEventAction())
                          .initial(ToolStates.IDLE_CHOICE)
                              .and()

          .withStates()
              .parent(ToolStates.OPERATING)
                  .state(ToolStates.OPERATING_CHOICE)
                      .stateEntry(ToolStates.LOADING_PROCESS, new ProcessNewPartAction())
                          .state(ToolStates.PROCESSING_PART)
                              .stateEntry(ToolStates.UNLOADING_PROCESS, new EjectFinishedPartAction())
                                  .initial(ToolStates.OPERATING_CHOICE)
                                      .end(ToolStates.OPERATING_END);
                  ;

      builder.configureTransitions()
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
                  .target(ToolStates.UP_CHOICE)
                      .event(ToolEvents.START)
                          .and()

          .withChoice()
              .source(ToolStates.UP_CHOICE)
                  .first(ToolStates.OPERATING, new FlowIsFreeGuard())
                      .last(ToolStates.IDLE)
                          .and()

          .withExternal()
              .source(ToolStates.IDLE)
                  .target(ToolStates.STOPPED)
                      .event(ToolEvents.STOP)
                         .and()

          .withExternal()
              .source(ToolStates.IDLE)
                  .target(ToolStates.OPERATING)
                      .guard(new FlowIsFreeGuard())
                          .and()

          .withExternal()
              .source(ToolStates.OPERATING)
                  .target(ToolStates.IDLE)
          .guard(new FlowIsNotFreeGuard())
                      .and()

          .withExternal()
              .source(ToolStates.OPERATING)
                  .target(ToolStates.STOPPED)
                      .event(ToolEvents.STOP)
                          .and()

          .withChoice()
              .source(ToolStates.IDLE_CHOICE)
                  .first(ToolStates.IDLE_UPSTREAM, new InportEmptyGuard())
                      .last(ToolStates.IDLE_DOWNSTREAM)
                          .and()

          .withChoice()
              .source(ToolStates.OPERATING_CHOICE)
                  .first(ToolStates.LOADING_PROCESS, new ProcessEmptyGuard())
                      .last(ToolStates.UNLOADING_PROCESS)
                          .and()

          .withExternal()
              .source(ToolStates.LOADING_PROCESS)
                  .target(ToolStates.PROCESSING_PART)
                      .and()

          .withExternal()
              .source(ToolStates.PROCESSING_PART)
                  .target(ToolStates.UNLOADING_PROCESS)
                      .timerOnce(ToolDefault.CYCLE_TIME)
                        .and()

          .withExternal()
              .source(ToolStates.UNLOADING_PROCESS)
                  .target(ToolStates.LOADING_PROCESS)
                      .guard(new FlowIsFreeGuard())
                          .and()

          .withExternal()
              .source(ToolStates.UNLOADING_PROCESS)
                  .target(ToolStates.OPERATING_END)
                      .guard(new InportEmptyGuard())
                          .and()

          .withExternal()
              .source(ToolStates.UNLOADING_PROCESS)
                  .target(ToolStates.OPERATING_END)
                      .guard(new OutportFullGuard())
          ;

      builder.configureConfiguration()
          .withConfiguration()
          .listener(new StateMachineListener());

      return builder.build();
    }

    public void start() {
      this.stateMachine.start();
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
