package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.ToolIdleEvent;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ToolIdleEventUpstreamAction implements Action<ToolStates, ToolEvents> {
    @Override
    public void execute(final StateContext<ToolStates, ToolEvents> context) {
        System.out.println("Tool issues ToolIdleEvent with UPSTREAM reason...");
        //TODO: get toolId from StateContext
        final String toolId = "Bruder Willibald";
        ToolIdleEvent domainEvent = new ToolIdleEvent(toolId, IdleReason.UPSTREAM);
        // TODO: put domainEvent in StateContext
    }
}
