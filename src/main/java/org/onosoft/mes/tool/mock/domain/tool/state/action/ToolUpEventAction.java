package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.ToolUpEvent;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.tool.state.StateVarUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ToolUpEventAction
    extends ToolAction
    implements Action<ToolStates, ToolEvents> {
  @Override
  public void execute(StateContext<ToolStates, ToolEvents> stateContext) {

    ToolId toolId = StateVarUtil.getToolId(stateContext);
    this.events.add(new ToolUpEvent(toolId));
    StateVarUtil.setDomainEvents(stateContext, this.events);
  }
}
