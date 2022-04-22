package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.PartInProcessEvent;
import org.onosoft.mes.tool.mock.domain.event.PartProcessedEvent;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;
import org.onosoft.mes.tool.mock.domain.tool.entity.Process;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Take finished part from process,
 *    issue PartProcessedEvent for it,
 *    load it to outport.
 *
 * Take next part from inport,
 *    issue PartInProcessEvent for it,
 *    load it to process.
 *
 */
public class ProcessPartAction implements Action<ToolStates, ToolEvents> {

  protected StateContext<ToolStates, ToolEvents> context;
  protected LoadPort inport;
  protected LoadPort outport;
  protected Process process;
  protected List<DomainEvent> events;

  @Override
  public void execute(final StateContext<ToolStates, ToolEvents> context){

    this.inport = StateContextVariableUtil.getInport(context);
    this.outport = StateContextVariableUtil.getOutport(context);
    this.process = StateContextVariableUtil.getProcess(context);
    this.events = new ArrayList<>();

    this.handleFinishedPart();
    this.handleNewPart();

    StateContextVariableUtil.setDomainEvents(context, events);
  }

  protected void handleFinishedPart() {
    Part finishedPart = this.process.eject();
    PartProcessedEvent processedEvent = new PartProcessedEvent(
        finishedPart,
        this.process.getId());
    this.events.add(processedEvent);
    try {
      this.outport.load(finishedPart);
    } catch (LoadportFullException e) {
      StateContextVariableUtil.setApplicationException(context, e);
    }
  }

  protected void handleNewPart() {
    try {
      Part newPart = this.inport.next();
      PartInProcessEvent inProcessEvent = new PartInProcessEvent(
          newPart,
          this.process.getId());
      this.events.add(inProcessEvent);

    } catch (NoPartAvailableException e) {
      StateContextVariableUtil.setApplicationException(this.context, e);
    }
  }
}
