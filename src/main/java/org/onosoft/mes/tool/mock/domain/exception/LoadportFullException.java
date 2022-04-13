package org.onosoft.mes.tool.mock.domain.exception;

import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;

@Getter

public class LoadportFullException extends ApplicationException {
    protected final LoadportId portId;
    protected final Part rejectedPart;

    public LoadportFullException(ToolId tool, LoadportId port, Part rejectedPart) {
        super(tool);
        this.portId = port;
        this.rejectedPart = rejectedPart;
    }
}
