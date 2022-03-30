package org.onosoft.mes.tool.mock.domain.tool.state;

import org.onosoft.mes.tool.mock.domain.event.EventBundle;
import org.onosoft.mes.tool.mock.domain.event.PartBundle;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.ToolInputBufferFullException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.tool.ToolImpl;
import org.onosoft.mes.tool.mock.domain.provided.value.DownTimeReason;
import org.onosoft.mes.tool.mock.domain.value.DomainEvent;

import java.util.List;

public interface ToolState {



    public EventBundle start(StateContext tool);
    public EventBundle stop(StateContext tool, DownTimeReason reason);
    public EventBundle loadPart(StateContext tool, Part part) throws ToolInputBufferFullException;
    public PartBundle unloadPart(StateContext tool) throws NoPartAvailableException;
    public EventBundle breakDown(StateContext tool);
    public EventBundle repair(StateContext tool);

    void changeStateTo(ToolState target);

}
