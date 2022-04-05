package org.onosoft.mes.tool.mock.domain.provided;

import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;

public interface Tool {
    void start();
    void stop(IdleReason reason);
    void loadPart(Part part) throws LoadportFullException;
    Part unloadPart() throws NoPartAvailableException;
    void breakDown();
    void repair();
}