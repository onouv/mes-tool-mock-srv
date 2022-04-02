package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.provided.value.DowntimeReason;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.state.event.FSMEvent;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class IssueToolIdleEventWithUPSTREAM implements Action<ToolStates, FSMEvent> {
    @Override
    public void execute(final StateContext<ToolStates, FSMEvent> context) {
        System.out.println("FSMEvent triggered IsseToolIdleEventAction with UPSTREAM reason...");
    }
}
