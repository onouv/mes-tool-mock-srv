package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.PartProcessedEvent;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.ProcessEmptyGuard;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;


/**
 *  Take finished part from process,
 *     publish PartProcessedEvent for it directly to message bus
 *     load it to outport - issue LoadPortFullException to domain if applicable
 */
public class EjectFinishedPartAction extends ToolAction {
  private static final Logger logger= LoggerFactory.getLogger(EjectFinishedPartAction.class);
  @Override
  public void execute(final StateContext<ToolStates, ToolEvents> context){

    this.init(context);
    Part finishedPart = this.process.eject();
    PartProcessedEvent processedEvent = new PartProcessedEvent(
        finishedPart,
        this.process.getId());
    StateContextVariableUtil.publish(context, processedEvent);
    try {
      this.outport.load(finishedPart);
      // context.getStateMachine().sendEvent(ToolEvents.FINISHED);
      logger.debug("Tool id = %s ejects part ", toolId, finishedPart.getId());
    } catch (LoadportFullException e) {
      StateContextVariableUtil.setApplicationException(context, e);
    }
  }
}
