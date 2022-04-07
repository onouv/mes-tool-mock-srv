package org.onosoft.mes.tool.mock.domain.tool.state;

import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.tool.state.action.*;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory
public class ToolStateMachineConfig extends EnumStateMachineConfigurerAdapter<ToolStates, ToolEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<ToolStates, ToolEvents> states) throws Exception {
        states
            .withStates()
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
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ToolStates, ToolEvents> transitions) throws Exception {
        transitions
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
                .guard(new InportNotFullGuard())
                .action(new LoadPartAction())
                .and()
            .withInternal()
                .source(ToolStates.UP)
                .event(ToolEvents.PART_UNLOADING)
                .guard(new OutportNotEmptyGuard())
                .action(new UnloadPartAction())
                .and()
            .withInternal()
                .source(ToolStates.PROCESSING)
                .timer(Tool.getCycleTime())
                .guard(new InportNotFullGuard())
                .action(new LoadPartAction());

    }
}
