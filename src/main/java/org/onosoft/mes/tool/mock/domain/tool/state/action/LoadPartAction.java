package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.PartLoadedEvent;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.state.StateVarUtil;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.ArrayList;
import java.util.List;

public class LoadPartAction implements Action<ToolStates, ToolEvents> {

    @Override
    public void execute(final StateContext<ToolStates, ToolEvents> context) {
        System.out.println("Tool loads part and issues LoadPartEvent...");

        Part part = StateVarUtil.getPart(context);
        LoadPort inport = StateVarUtil.getInport(context);

        try{
            inport.load(part);
            List<DomainEvent> events = new ArrayList<DomainEvent>();
            events.add(new PartLoadedEvent(part));
            StateVarUtil.setDomainEvents(context, events);
        } catch (LoadportFullException e) {

            StateVarUtil.setApplicationException(context, e);
        }

    }
}
