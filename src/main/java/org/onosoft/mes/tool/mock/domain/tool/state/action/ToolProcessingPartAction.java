package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ToolProcessingPartAction implements Action<ToolStates, ToolEvents> {

  @Override
  public void execute(final StateContext<ToolStates, ToolEvents> context) {
    System.out.println("Tool processing part ToolIdleEvent with DOWNSTREAM reason...");


  }
}
