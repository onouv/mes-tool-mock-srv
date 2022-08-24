package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.adapters.in.web.status.ToolMockStatusController;
import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.ToolStoppedEvent;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.ArrayList;
import java.util.List;

public class  ToolStoppedEventAction
    extends ToolAction
    implements Action<ToolStates, ToolEvents> {

  private static final Logger logger= LoggerFactory.getLogger(ToolStoppedEventAction.class);
  @Override
  public void execute(StateContext<ToolStates, ToolEvents> stateContext) {
    logger.debug("Tool id=%s issues STOPPED event.", toolId);
    init(stateContext);
    events.add(new ToolStoppedEvent(toolId));
    finish();
  }
}