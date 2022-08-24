package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.PartInProcessEvent;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.ProcessEmptyGuard;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;

/**
 *
 * Take next part from inport,
 *    publish PartInProcessEvent for it directly to message bus
 *    load it to process - issue NoPartAvailableExcepyion to domain, if applicable
 *
 */
public class ProcessNewPartAction extends ToolAction{
  private static final Logger logger= LoggerFactory.getLogger(ProcessNewPartAction.class);
  @Override
  public void execute(final StateContext<ToolStates, ToolEvents> context){

    this.init(context);

    try {
      Part newPart = this.inport.next();
      this.process.run(newPart);
      logger.debug("ProcessNewPartAction:  processing part " + newPart.getId());
      PartInProcessEvent inProcessEvent = new PartInProcessEvent(
          newPart,
          this.process.getId());
      StateContextVariableUtil.publish(context, inProcessEvent);

    } catch (NoPartAvailableException e) {
      StateContextVariableUtil.setApplicationException(this.context, e);
    }
  }

}
