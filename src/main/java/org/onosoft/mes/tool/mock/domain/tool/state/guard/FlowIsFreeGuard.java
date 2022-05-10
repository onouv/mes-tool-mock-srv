package org.onosoft.mes.tool.mock.domain.tool.state.guard;

import org.onosoft.mes.tool.mock.adapters.in.web.parts.ToolMockPartsController;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;


public class FlowIsFreeGuard implements Guard<ToolStates, ToolEvents> {

  private static final Logger logger= LoggerFactory.getLogger(FlowIsFreeGuard.class);
  @Override
  public boolean evaluate(StateContext<ToolStates, ToolEvents> context) {
    PortStatus inport = StateContextVariableUtil.getInport(context);
    PortStatus outport = StateContextVariableUtil.getOutport(context);
    if((! outport.isFull()) && (!inport.isEmpty())) {
      logger.info("Guard:  Material flow found unblocked.");
      return true;
    } else {
      logger.info("Guard: Material flow found blocked.");
      return false;
    }
  }
}