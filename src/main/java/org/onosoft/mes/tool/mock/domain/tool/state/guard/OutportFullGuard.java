package org.onosoft.mes.tool.mock.domain.tool.state.guard;

import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public class OutportFullGuard implements Guard<ToolStates, ToolEvents> {
  @Override
  public boolean evaluate(StateContext<ToolStates, ToolEvents> context) {
    PortStatus port = StateContextVariableUtil.getOutport(context);
    return port.isFull();
  }
}
