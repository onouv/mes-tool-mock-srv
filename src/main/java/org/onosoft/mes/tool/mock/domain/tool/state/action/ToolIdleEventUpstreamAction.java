package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.ToolIdleEvent;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.ArrayList;
import java.util.List;

public class ToolIdleEventUpstreamAction
    extends ToolAction
    implements Action<ToolStates, ToolEvents> {

    private static final Logger logger= LoggerFactory.getLogger(ToolIdleEventUpstreamAction.class);
    @Override
    public void execute(final StateContext<ToolStates, ToolEvents> context) {
        init(context);
        logger.debug("Tool id=%s issues ToolIdleEvent with UPSTREAM reason.", toolId);
        events.add(new ToolIdleEvent(toolId, IdleReason.UPSTREAM));
        finish();
    }
}
