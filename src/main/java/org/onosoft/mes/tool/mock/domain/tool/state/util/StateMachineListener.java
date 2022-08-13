package org.onosoft.mes.tool.mock.domain.tool.state.util;

import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

public class StateMachineListener
    extends StateMachineListenerAdapter<ToolStates, ToolEvents> {
  private static final Logger logger= LoggerFactory.getLogger(StateMachineListener.class);
  @Override
  public void stateChanged(State from, State to) {
      String fromId = (from != null) ? from.getId().toString() : "Nirvana";
      String toId = (to != null) ? to.getId().toString() : "Nirvana";
      logger.info(String.format("tool state transition from: %s to: %s\n", fromId, toId));
  }
}
