package org.onosoft.mes.tool.mock.domain.tool;

import org.onosoft.mes.tool.mock.domain.event.PartInProcessEvent;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.action.ToolAction;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class BeginProcessAction extends ToolAction implements Action<ToolStates, ToolEvents> {

  @Override
  public void execute(final StateContext<ToolStates, ToolEvents> context) {

    LoadPort inport = StateContextVariableUtil.getInport(context);


    try {
      Part newPart = inport.next();
      PartInProcessEvent inProcessEvent = new PartInProcessEvent(
          newPart,
          this.process.getId());
      this.events.add(inProcessEvent);

    } catch (NoPartAvailableException e) {
      StateContextVariableUtil.setApplicationException(this.context, e);
    }
  }
}
