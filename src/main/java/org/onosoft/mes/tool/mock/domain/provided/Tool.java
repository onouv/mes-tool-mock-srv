package org.onosoft.mes.tool.mock.domain.provided;

import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;
import org.onosoft.mes.tool.mock.domain.value.DomainResult;

public interface Tool {
    DomainResult start();
    DomainResult stop(IdleReason reason);
    DomainResult loadPart(Part part, LoadportId portId) throws LoadportFullException;
    DomainResult unloadPart(LoadportId portId) throws NoPartAvailableException;
    DomainResult breakDown();
    DomainResult repair();
}