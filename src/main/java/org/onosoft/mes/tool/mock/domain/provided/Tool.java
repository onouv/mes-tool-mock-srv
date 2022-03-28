package org.onosoft.mes.tool.mock.domain.provided;

import lombok.Getter;
import org.onosoft.ddd.annotations.AggregateRoot;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.ToolInputBufferFullException;
import org.onosoft.mes.tool.mock.domain.tool.value.DownTimeReason;
import org.onosoft.mes.tool.mock.domain.tool.value.ToolStatus;

@AggregateRoot
@Getter
public abstract class Tool {

    protected String id;
    protected ToolStatus status = ToolStatus.DOWN;
    protected DownTimeReason downTimeReason = null;

    protected Tool() {
        this.id = null;
    }

    protected Tool(String id) {
        this.id = id;
    }

    public abstract void start();
    public abstract void stop(DownTimeReason reason);
    public abstract void loadPart(Part part) throws ToolInputBufferFullException;
    public abstract Part unloadPart() throws NoPartAvailableException;
    public abstract void breakDown();
    public abstract void repair();

}
