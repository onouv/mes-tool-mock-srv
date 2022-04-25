package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class CycleTimeElapsedAction implements Action<ToolStates, ToolEvents> {
  @Override
  public void execute(final StateContext<ToolStates, ToolEvents> context){
    context.getStateMachine().sendEvent(ToolEvents.FINISHED);
  }
}
