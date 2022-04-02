package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.tool.state.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.state.event.STOP;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class IssueToolUpEventAction implements Action<ToolStates, STOP> {

    @Override
    public void execute(final StateContext<ToolStates, STOP> context) {
        System.out.println("STOP event triggers IsseToolUpEventAction...");
    }
}
