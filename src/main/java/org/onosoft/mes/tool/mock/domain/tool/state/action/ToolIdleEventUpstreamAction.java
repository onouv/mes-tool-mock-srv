package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.ToolIdleEvent;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.tool.state.StateVarUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ToolIdleEventUpstreamAction
    extends ToolAction
    implements Action<ToolStates, ToolEvents> {

    @Override
    public void execute(final StateContext<ToolStates, ToolEvents> context) {
        ToolId toolId = StateVarUtil.getToolId(context);
        ToolIdleEvent domainEvent = new ToolIdleEvent(toolId, IdleReason.UPSTREAM);
        this.events.add(domainEvent);
        StateVarUtil.setDomainEvents(context, this.events);
    }
}
