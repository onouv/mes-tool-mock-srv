package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.PartLoadedEvent;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.ProcessEmptyGuard;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;

import java.util.ArrayList;
import java.util.List;

public class LoadPartAction extends ToolAction {
    private static final Logger logger= LoggerFactory.getLogger(LoadPartAction.class);

    @Override
    public void execute(final StateContext<ToolStates, ToolEvents> context) {
        init(context);
        Part part = StateContextVariableUtil.getPart(context);
        try{
            inport.load(part);
            events.add(new PartLoadedEvent(part));
            finish();
            logger.debug("LoadPartAction: Tool id={} loaded part id={}", toolId, part.getId());
        } catch (LoadportFullException e) {
            StateContextVariableUtil.setApplicationException(context, e);
        }

    }
}
