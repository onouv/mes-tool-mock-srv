package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.ToolUpEvent;

import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;

public class ToolUpEventAction
    extends ToolAction {

  private static final Logger logger= LoggerFactory.getLogger(ToolUpEventAction.class);
  @Override
  public void execute(StateContext<ToolStates, ToolEvents> stateContext) {
    init(stateContext);
    events.add(new ToolUpEvent(toolId));
    logger.debug("Tool id={} issues UP event.", toolId.toString());
    finish();
  }
}
