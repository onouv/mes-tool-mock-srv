package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ToolBeginProcessingPartAction implements Action<ToolStates, ToolEvents> {

  @Override
  public void execute(final StateContext<ToolStates, ToolEvents> context){
    System.out.println("Tool begins processing part ...");


  }
}
