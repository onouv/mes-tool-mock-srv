package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.entity.Process;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.onosoft.mes.tool.mock.domain.value.DomainResult;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.ArrayList;
import java.util.List;

public abstract class ToolAction implements Action<ToolStates, ToolEvents> {
  protected StateContext<ToolStates, ToolEvents> context;
  protected ToolId toolId;
  protected LoadPort inport;
  protected LoadPort outport;
  protected Process process;
  protected List<DomainEvent> events = new ArrayList<>();

  protected void init(StateContext<ToolStates, ToolEvents> context) {
    this.context = context;
    this.toolId = StateContextVariableUtil.getToolId(context);
    this.inport = StateContextVariableUtil.getInport(context);
    this.outport = StateContextVariableUtil.getOutport(context);
    this.process = StateContextVariableUtil.getProcess(context);
  }

  protected void finish() {
    StateContextVariableUtil.queueDomainEvents(this.context, this.events);
  }

}
