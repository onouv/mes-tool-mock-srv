package org.onosoft.mes.tool.mock.domain.tool.state.util;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.entity.Process;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.springframework.statemachine.StateContext;

import java.util.List;

public class StateContextVariableUtil {
  public static LoadPort getOutport(StateContext<ToolStates, ToolEvents> context) {
    return (LoadPort) context.getExtendedState().getVariables().get(StateVariableKeys.outport);
  }

  public static LoadPort getInport(StateContext<ToolStates, ToolEvents> context) {
    return (LoadPort) context.getExtendedState().getVariables().get(StateVariableKeys.inport);
  }

  public static ToolId getToolId(StateContext<ToolStates, ToolEvents> context) {
    return (ToolId) context.getExtendedState().getVariables().get(StateVariableKeys.toolId);
  }

  public static Process getProcess(StateContext<ToolStates, ToolEvents> context) {
    return (Process) context.getExtendedState().getVariables().get(StateVariableKeys.process);
  }

  public static Part getPart(StateContext<ToolStates, ToolEvents> context) {
    return (Part) context.getExtendedState().getVariables().get(StateVariableKeys.part);
  }

  public static void setPart(StateContext<ToolStates, ToolEvents> context, Part part) {
    context.getExtendedState().getVariables().put(StateVariableKeys.part, part);
  }

  public static void setApplicationException(StateContext<ToolStates, ToolEvents> context, ApplicationException ex) {
    context.getExtendedState().getVariables().put(StateVariableKeys.exception, ex);
  }

  public static void setDomainEvents(StateContext<ToolStates, ToolEvents> context, List<DomainEvent> events) {
    context.getExtendedState().getVariables().put(StateVariableKeys.domainEvents, events);
  }
}
