package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.PartUnloadedEvent;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.state.StateVarUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.ArrayList;
import java.util.List;

public class UnloadPartAction
    extends ToolAction
    implements Action<ToolStates, ToolEvents> {

  @Override
  public void execute(final StateContext<ToolStates, ToolEvents> context) {
    System.out.println("Tool issues ToolIdleEvent with DOWNSTREAM reason...");

    LoadPort outport = StateVarUtil.getOutport(context);
    ToolId toolId = StateVarUtil.getToolId(context);

    try {
      Part unloaded = outport.next();
      PartUnloadedEvent event = new PartUnloadedEvent(unloaded.getPartId());
      List<DomainEvent> events = new ArrayList<>();
      events.add(event);
      StateVarUtil.setDomainEvents(context, events);
    }
    catch(NoPartAvailableException e) {
      StateVarUtil.setApplicationException(context, e);
    }
  }
}
