package org.onosoft.mes.tool.mock.domain.tool.state;

import org.onosoft.mes.tool.mock.domain.event.EventBundle;
import org.onosoft.mes.tool.mock.domain.event.PartBundle;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.ToolInputBufferFullException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.DownTimeReason;
import org.onosoft.mes.tool.mock.domain.tool.ToolImpl;
import org.onosoft.mes.tool.mock.domain.value.DomainEvent;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class StoppedState implements ToolState {

    static StoppedState instance;

    public static ToolState instance() {
        if (instance == null) {
            instance = new StoppedState();
        }
        return instance;

    }

    @Override
    public EventBundle start(StateContext tool) {

        // TODO (1)

    }

    @Override
    public EventBundle stop(StateContext tool, DownTimeReason reason){

    }

    @Override
    public EventBundle loadPart(StateContext tool, Part part) throws ToolInputBufferFullException {

        final boolean inputBufferFull = tool.getProcess().offer(part);

        if(inputBufferFull) {
            throw new ToolInputBufferFullException(tool, part);
        }

        // TODO: issue PartLoadedEvent(part.getId(), tool.getId())

        // internal transition, no state change
    }

    @Override
    public PartBundle unloadPart(StateContext ctx) throws NoPartAvailableException {

        Tool tool = ctx.getTool();
        Part taken = tool.getProcess().poll();
        if(taken != null) {

            // TODO: issue PartUnLoadedEvent(part.getId(), tool.getId())

            // internal transition, no state change, no DomainEvents
            return new PartBundle(tool, taken, new ArrayList<DomainEvent>());
        } else {
            throw new NoPartAvailableException(tool);
        }
    }

    @Override
    public EventBundle breakDown(StateContext tool) {

    }

    @Override
    public EventBundle repair(StateContext tool) {

    }

}
