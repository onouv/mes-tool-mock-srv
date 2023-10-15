package org.onosoft.mes.tool.mock.domain.tool.state.guard;

import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public class OutportNotFullGuard implements Guard<ToolStates, ToolEvents> {

  private static final Logger logger= LoggerFactory.getLogger(OutportFullGuard.class);
  @Override
  public boolean evaluate(StateContext<ToolStates, ToolEvents> context) {
    PortStatus port = StateContextVariableUtil.getOutport(context);

    if( ! port.isFull()) {
      logger.debug("OutportNotFullGuard: Outport found not full --> true.");
      return true;
    } else {
      logger.debug("OutportNotFullGuard: Outport found full --> false.");
      return false;
    }
  }
}