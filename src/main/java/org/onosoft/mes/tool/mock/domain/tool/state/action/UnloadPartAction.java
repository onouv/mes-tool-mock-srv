package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class UnloadPartAction
    extends ToolAction
    implements Action<ToolStates, ToolEvents> {

  @Override
  public void execute(final StateContext<ToolStates, ToolEvents> context){
    System.out.println("Tool issues ToolIdleEvent with DOWNSTREAM reason...");


    Part unloaded = this.outport.next();
    if(unloaded == null)
      throw new NoPartAvailableException(this.id, this.outport.getId());

  }
}
