package org.onosoft.mes.tool.mock.domain.provided;

import org.onosoft.mes.tool.mock.domain.exception.IllegalLoadportTypeException;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
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
    DomainResult loadPart(Part part, LoadportId portId)
        throws IllegalLoadportTypeException;
    DomainResult unloadPart(LoadportId portId)
        throws IllegalLoadportTypeException;
    DomainResult breakDown();
    DomainResult repair();

    ToolId getId();
    String getName();
    String getDescription();
    List<ToolStates> getStates();

    LoadPort getInport();
    LoadPort getOutport();

    List<PartId> getPartsInProcess();
}