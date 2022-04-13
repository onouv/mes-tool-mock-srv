package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.tool.state.StateVarKeys;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ToolStoppedEventAction implements Action<ToolStates, ToolEvents> {
  @Override
  public void execute(StateContext<ToolStates, ToolEvents> stateContext) {

    String tool = (String) stateContext.getExtendedState().getVariables().get(StateVarKeys.toolId);
    System.out.println(String.format("Tool %s issues ToolStoppedEvent...", tool));

  }
}