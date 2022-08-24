package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.ToolStoppedEvent;
import org.onosoft.mes.tool.mock.domain.event.ToolUpEvent;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;

import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.ArrayList;
import java.util.List;

public class ToolUpEventAction
    extends ToolAction
    implements Action<ToolStates, ToolEvents> {
  @Override
  public void execute(StateContext<ToolStates, ToolEvents> stateContext) {
    init(stateContext);
    events.add(new ToolUpEvent(toolId));
    finish();
  }
}
