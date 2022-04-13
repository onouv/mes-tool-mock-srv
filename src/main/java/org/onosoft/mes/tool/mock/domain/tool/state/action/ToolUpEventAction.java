package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.tool.state.StateVarUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ToolUpEventAction implements Action<ToolStates, ToolEvents> {
  @Override
  public void execute(StateContext<ToolStates, ToolEvents> stateContext) {

    ToolId toolId = StateVarUtil.getToolId(stateContext);

    System.out.println(String.format("Tool %s issues ToolUpEvent...", toolId));
  }
}