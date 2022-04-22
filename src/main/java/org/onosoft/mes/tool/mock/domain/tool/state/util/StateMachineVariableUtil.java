package org.onosoft.mes.tool.mock.domain.tool.state.util;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.entity.Process;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.springframework.statemachine.StateMachine;

import java.util.List;

public class StateMachineVariableUtil {

  public static void clearAll(StateMachine<ToolStates, ToolEvents> fsm) {
    fsm.getExtendedState().getVariables().clear();
  }

  public static void setOutport(StateMachine<ToolStates, ToolEvents> fsm, LoadPort port) {
    fsm.getExtendedState().getVariables().put(StateVariableKeys.outport, port);
  }

  public static void setInport(StateMachine<ToolStates, ToolEvents> fsm, LoadPort port) {
    fsm.getExtendedState().getVariables().put(StateVariableKeys.inport, port);
  }

  public static void setToolId(StateMachine<ToolStates, ToolEvents> fsm, ToolId toolId) {
    fsm.getExtendedState().getVariables().put(StateVariableKeys.toolId, toolId);
  }

  public static void setProcess(StateMachine<ToolStates, ToolEvents> fsm, Process process) {
    fsm.getExtendedState().getVariables().put(StateVariableKeys.process, process);
  }

  public static Part getPart(StateMachine<ToolStates, ToolEvents> fsm) {
    return (Part) fsm.getExtendedState().getVariables().get(StateVariableKeys.part);
  }

  public static void setPart(StateMachine<ToolStates, ToolEvents> fsm, Part part) {
    fsm.getExtendedState().getVariables().put(StateVariableKeys.part, part);
  }

  public static ApplicationException getApplicationException(StateMachine<ToolStates, ToolEvents> fsm)  {
    return (ApplicationException) fsm.getExtendedState().getVariables().get(StateVariableKeys.exception);
  }

  public static List<DomainEvent> getDomainEvents(StateMachine<ToolStates, ToolEvents> ctx) {
    return (List<DomainEvent>) ctx.getExtendedState().getVariables().get(StateVariableKeys.domainEvents);
  }
}
