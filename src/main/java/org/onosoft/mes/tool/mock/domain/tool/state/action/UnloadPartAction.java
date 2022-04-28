package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.PartUnloadedEvent;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.ArrayList;
import java.util.List;

public class UnloadPartAction
    extends ToolAction
    implements Action<ToolStates, ToolEvents> {

  @Override
  public void execute(final StateContext<ToolStates, ToolEvents> context) {
    LoadPort outport = StateContextVariableUtil.getOutport(context);
    ToolId toolId = StateContextVariableUtil.getToolId(context);

    try {
      Part unloaded = outport.next();
      PartUnloadedEvent event = new PartUnloadedEvent(unloaded);

      List<DomainEvent> events = new ArrayList<>();
      events.add(event);
      StateContextVariableUtil.setDomainEvents(context, events);
    }
    catch(NoPartAvailableException e) {
      StateContextVariableUtil.setApplicationException(context, e);
    }
  }
}
