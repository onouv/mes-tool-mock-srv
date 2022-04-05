package org.onosoft.mes.tool.mock.domain.tool.state;

import org.onosoft.mes.tool.mock.domain.tool.state.action.ToolIdleEventDownStreamAction;
import org.onosoft.mes.tool.mock.domain.tool.state.action.ToolIdleEventUpstreamAction;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.FlowIsFreeGuard;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.InportEmptyGuard;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.OutportFullGuard;
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
            .state(ToolStates.DOWN)
            .and()
            .withStates()
                .parent(ToolStates.UP)
                .initial(ToolStates.STOPPED)
                .state(ToolStates.IDLE)
                .state(ToolStates.PROCESSING);

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
             ;

    }

}
