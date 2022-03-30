package org.onosoft.mes.tool.mock.domain.provided

import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.ToolInputBufferFullException;
import org.onosoft.mes.tool.mock.domain.provided.value.DownTimeReason;

public interface Tool {
    public void start();
    public void stop(DownTimeReason reason);
    public void loadPart(Part part) throws ToolInputBufferFullException;
    public Part unloadPart() throws NoPartAvailableException;
    public void breakDown();
    public void repair();
}