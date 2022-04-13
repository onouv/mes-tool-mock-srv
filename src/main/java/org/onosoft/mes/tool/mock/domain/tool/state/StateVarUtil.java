package org.onosoft.mes.tool.mock.domain.tool.state;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.entity.Process;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;

import java.util.List;

public class StateVarUtil {

  public enum Keys {
    outport,
    inport,
    toolId,
    process,
    part,
    domainEvents,
    exception
  }

  public static LoadPort getOutport(StateContext<ToolStates, ToolEvents> context) {
    return (LoadPort) context.getExtendedState().getVariables().get(Keys.outport);
  }

  public static void setOutport(StateMachine<ToolStates, ToolEvents> fsm, LoadPort port) {
    fsm.getExtendedState().getVariables().put(Keys.outport, port);
  }

  public static LoadPort getInport(StateContext<ToolStates, ToolEvents> context) {
    return (LoadPort) context.getExtendedState().getVariables().get(Keys.inport);
  }

  public static void setInport(StateMachine<ToolStates, ToolEvents> fsm, LoadPort port) {
    fsm.getExtendedState().getVariables().put(Keys.inport, port);
  }

  public static ToolId getToolId(StateContext<ToolStates, ToolEvents> context) {
    return (ToolId) context.getExtendedState().getVariables().get(Keys.toolId);
  }

  public static void setToolId(StateMachine<ToolStates, ToolEvents> fsm, ToolId toolId) {
    fsm.getExtendedState().getVariables().put(Keys.toolId, toolId);
  }

  public static Process getProcess(StateContext<ToolStates, ToolEvents> context) {
    return (Process) context.getExtendedState().getVariables().get(Keys.process);
  }

  public static void setProcess(StateMachine<ToolStates, ToolEvents> fsm, Process process) {
    fsm.getExtendedState().getVariables().put(Keys.process, process);
  }

  public static Part getPart(StateMachine<ToolStates, ToolEvents> fsm) {
    return (Part) fsm.getExtendedState().getVariables().get(Keys.part);
  }

  public static Part getPart(StateContext<ToolStates, ToolEvents> context) {
    return (Part) context.getExtendedState().getVariables().get(Keys.part);
  }

  public static void setPart(StateMachine<ToolStates, ToolEvents> fsm, Part part) {
    fsm.getExtendedState().getVariables().put(Keys.part, part);
  }

  public static void setPart(StateContext<ToolStates, ToolEvents> context, Part part) {
    context.getExtendedState().getVariables().put(Keys.part, part);
  }

  public static void setDomainEvents(StateContext<ToolStates, ToolEvents> context, List<DomainEvent> events) {
    context.getExtendedState().getVariables().put(Keys.domainEvents, events);
  }

  public static List<DomainEvent> getDomainEvents(StateMachine<ToolStates, ToolEvents> fsm) {
    return (List<DomainEvent>) fsm.getExtendedState().getVariables().get(Keys.domainEvents);
  }


  public static void setApplicationException(StateContext<ToolStates, ToolEvents> context, ApplicationException ex) {
    context.getExtendedState().getVariables().put(Keys.exception, ex);
  }

  public static ApplicationException getApplicationException(StateMachine<ToolStates, ToolEvents> fsm)  {
    return (ApplicationException) fsm.getExtendedState().getVariables().get(Keys.exception);
  }

}
