package org.onosoft.mes.tool.mock.domain.provided;

import org.onosoft.mes.tool.mock.domain.event.EventBundle;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;

public interface Tool {
    EventBundle start();
    EventBundle stop(IdleReason reason);
    EventBundle loadPart(Part part, LoadportId portId) throws LoadportFullException;
    EventBundle unloadPart(LoadportId portId) throws NoPartAvailableException;
    EventBundle breakDown();
    EventBundle repair();
}