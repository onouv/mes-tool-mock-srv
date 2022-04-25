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
 *
 * Take next part from inport,
 *    publish PartInProcessEvent for it directly to message bus
 *    load it to process - issue NoPartAvailableExcepyion to domain, if applicable
 *
 */
public class ProcessNewPartAction extends ToolAction{

  @Override
  public void execute(final StateContext<ToolStates, ToolEvents> context){

    this.init(context);

    try {
      Part newPart = this.inport.next();
      PartInProcessEvent inProcessEvent = new PartInProcessEvent(
          newPart,
          this.process.getId());
      StateContextVariableUtil.publish(context, inProcessEvent);

    } catch (NoPartAvailableException e) {
      StateContextVariableUtil.setApplicationException(this.context, e);
    }
  }

}
