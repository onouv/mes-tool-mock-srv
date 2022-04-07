package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolStates;
import org.springframework.statemachine.StateContext;

public abstract class ToolAction {

  protected void getStateVars(final StateContext<ToolStates, ToolEvents> context) {
    return context.getExtendedState().getVariables()
  }
}
