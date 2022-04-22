package org.onosoft.mes.tool.mock.domain.tool.state.action;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.PartLoadedEvent;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.tool.state.util.StateContextVariableUtil;
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

        Part part = StateContextVariableUtil.getPart(context);
        LoadPort inport = StateContextVariableUtil.getInport(context);

        try{
            inport.load(part);
            List<DomainEvent> events = new ArrayList<>();
            events.add(new PartLoadedEvent(part));
            StateContextVariableUtil.setDomainEvents(context, events);
        } catch (LoadportFullException e) {

            StateContextVariableUtil.setApplicationException(context, e);
        }

    }
}
