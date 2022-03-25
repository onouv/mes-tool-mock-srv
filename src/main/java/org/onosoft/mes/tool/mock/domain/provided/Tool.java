package org.onosoft.mes.tool.mock.domain.provided;

import org.onosoft.ddd.annotations.AggregateRoot;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.ToolInputBufferFullException;
import org.onosoft.mes.tool.mock.domain.tool.value.DownTimeReason;
import org.onosoft.mes.tool.mock.domain.tool.value.ToolStatus;

@AggregateRoot
public abstract class Tool {

    protected String toolId;
    protected ToolStatus status = ToolStatus.DOWN;
    protected DownTimeReason downTimeReason = null;

    public abstract void start();
    public abstract void stop(DownTimeReason reason);
    public abstract void loadPart(Part part) throws ToolInputBufferFullException;
    public abstract Part unloadPart() throws NoPartAvailableException;

}
