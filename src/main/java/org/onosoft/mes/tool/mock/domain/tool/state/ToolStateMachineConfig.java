package org.onosoft.mes.tool.mock.domain.tool.state;

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
                // TODO: InportEmptyGuard
                // TODO: IdleForUpstreamAction
                .event(ToolEvents.START)
                .and()
            .withExternal()
                .source(ToolStates.STOPPED)
                .target(ToolStates.IDLE)
                // TODO: OutportFullGuard
                // TODO: IdleForDownstreamAction
                .event(ToolEvents.START)
                .and()
            .withExternal()
                .source(ToolStates.STOPPED)
                .target(ToolStates.PROCESSING)
                // TODO: FlowIsFreeGuard
                .event(ToolEvents.START)
                .and()
            .withExternal()
                .source(ToolStates.IDLE)
                .target(ToolStates.STOPPED)
                .event(ToolEvents.STOP)
                .and()
            .withExternal()
                .source(ToolStates.IDLE)
                .target(ToolStates.PROCESSING)
                // TODO:  FlowIsFreeGuard
                .and()
            .withExternal()
                .source(ToolStates.PROCESSING)
                .target(ToolStates.IDLE)
                .event(ToolEvents.FINISHED)
                // TODO: InportEmptyGuard
                // TODO: IdleForUpstreamAction
                .and()
            .withExternal()
                .source(ToolStates.PROCESSING)
                .target(ToolStates.IDLE)
                .event(ToolEvents.FINISHED)
                // TODO: OutportFullGuard
                // TODO: IdleForDownstreamAction
             ;

    }

}
