package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.tool.state.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.state.event.PART_LOAD;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class LoadPartAction implements Action<ToolStates, PART_LOAD> {

    @Override
    public void execute(final StateContext<ToolStates, PART_LOAD> context) {
        System.out.println("STOP event triggers LoadPartAction...");
    }
}
