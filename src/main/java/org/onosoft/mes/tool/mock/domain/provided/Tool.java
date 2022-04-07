package org.onosoft.mes.tool.mock.domain.provided;

import org.onosoft.mes.tool.mock.domain.event.EventBundle;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;

public interface Tool {
    EventBundle start();
    EventBundle stop(IdleReason reason);
    EventBundle loadPart(Part part) throws LoadportFullException;
    EventBundle unloadPart() throws NoPartAvailableException;
    EventBundle breakDown();
    EventBundle repair();
    static int getCycleTime() {
        return 2200;
    }
}