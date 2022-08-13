package org.onosoft.mes.tool.mock.domain.tool.state.guard;

import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public class InportEmptyGuard implements Guard<ToolStates, ToolEvents> {
  private static final Logger logger= LoggerFactory.getLogger(InportEmptyGuard.class);

  @Override
  public boolean evaluate(StateContext<ToolStates, ToolEvents> context) {
    PortStatus port = StateContextVariableUtil.getInport(context);

    if(port.isEmpty()) {
      logger.info("Guard: Inport found empty.");
      return true;
    } else {
      logger.info("Guard: Inport found not empty.");
      return false;
    }
  }
}
