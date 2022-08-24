package org.onosoft.mes.tool.mock.domain.tool.state.guard;

import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;


public class FlowIsNotFreeGuard implements Guard<ToolStates, ToolEvents> {

  private static final Logger logger= LoggerFactory.getLogger(FlowIsNotFreeGuard.class);
  @Override
  public boolean evaluate(StateContext<ToolStates, ToolEvents> context) {
    PortStatus inport = StateContextVariableUtil.getInport(context);
    PortStatus outport = StateContextVariableUtil.getOutport(context);
    if((outport.isFull()) || (inport.isEmpty())) {
      logger.debug("FlowIsNotFreeGuard:  Material flow found blocked.");
      return true;
    } else {
      logger.debug("FlowIsNotFreeGuard:  Material flow found unblocked.");
      return false;
    }
  }
}