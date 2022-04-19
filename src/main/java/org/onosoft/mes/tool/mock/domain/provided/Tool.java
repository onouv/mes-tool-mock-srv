package org.onosoft.mes.tool.mock.domain.provided;

import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDefinitionDto;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.exception.ToolPreExistingException;
import org.onosoft.mes.tool.mock.domain.provided.value.*;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.onosoft.mes.tool.mock.domain.value.DomainResult;

import java.util.List;

public interface Tool {

    DomainResult create() throws ToolPreExistingException;
    DomainResult delete();
    DomainResult start();
    DomainResult stop();
    DomainResult loadPart(Part part, LoadportId portId);
    DomainResult unloadPart(LoadportId portId) throws NoPartAvailableException;
    DomainResult breakDown();
    DomainResult repair();

    ToolId getId();
    String getName();
    String getDescription();
    ToolStates getStatus();

    LoadPort getInport();
    LoadPort getOutport();

    List<PartId> getPartsInProcess();
}