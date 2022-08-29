package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.ToolIdleEvent;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ToolIdleEventDownStreamAction
    extends ToolAction
    implements Action<ToolStates, ToolEvents> {

  private static final Logger logger= LoggerFactory.getLogger(ToolIdleEventDownStreamAction.class);
  @Override
  public void execute(final StateContext<ToolStates, ToolEvents> context) {
    init(context);
    logger.debug("Tool id=%s issues ToolIdleEvent with DOWNSTREAM reason.", toolId);
    this.events.add(new ToolIdleEvent(toolId, IdleReason.DOWNSTREAM));
    finish();
  }
}
