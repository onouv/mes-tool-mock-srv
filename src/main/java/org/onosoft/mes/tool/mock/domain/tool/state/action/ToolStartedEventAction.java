package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.ToolStartedEvent;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;

public class ToolStartedEventAction extends ToolAction {
  private static final Logger logger= LoggerFactory.getLogger(ToolStartedEventAction.class);
  @Override
  public void execute(StateContext<ToolStates, ToolEvents> stateContext) {
    init(stateContext);
    logger.debug("Tool id=%s issues STARTED event.", toolId);
    events.add(new ToolStartedEvent(toolId));
    finish();
  }

}
