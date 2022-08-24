package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.ToolDownEvent;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.ArrayList;
import java.util.List;

public class ToolDownEventAction
    extends ToolAction
    implements Action<ToolStates, ToolEvents> {

    private static final Logger logger= LoggerFactory.getLogger(ToolDownEventAction.class);

    @Override
    public void execute(StateContext<ToolStates, ToolEvents> stateContext) {
        logger.debug("Tool id=%s issues down event", toolId);
        init(stateContext);
        events.add(new ToolDownEvent(toolId));
        finish();
    }
}

