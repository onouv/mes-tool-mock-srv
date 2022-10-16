package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.PartUnloadedEvent;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class UnloadPartAction
    extends ToolAction
    implements Action<ToolStates, ToolEvents> {

  private static final Logger logger= LoggerFactory.getLogger(UnloadPartAction.class);
  @Override
  public void execute(final StateContext<ToolStates, ToolEvents> context) {
    init(context);
    try {
      Part unloaded = outport.next();
      PartUnloadedEvent event = new PartUnloadedEvent(unloaded);
      events.add(event);
      finish();
      logger.debug("Tool id={} unloaded part id={}", toolId, unloaded.getId());
    }
    catch(NoPartAvailableException e) {
      StateContextVariableUtil.setApplicationException(context, e);
    }
  }
}
