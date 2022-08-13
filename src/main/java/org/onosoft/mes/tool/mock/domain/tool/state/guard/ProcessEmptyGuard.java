package org.onosoft.mes.tool.mock.domain.tool.state.guard;

import org.onosoft.mes.tool.mock.domain.tool.entity.Process;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;


public class ProcessEmptyGuard implements Guard<ToolStates, ToolEvents> {

  private static final Logger logger= LoggerFactory.getLogger(ProcessEmptyGuard.class);
  @Override
  public boolean evaluate(StateContext<ToolStates, ToolEvents> context) {
    Process process = StateContextVariableUtil.getProcess(context);
    if(!process.isEmpty()) {
      return true;
    } else {
      return false;
    }
  }
}