package org.onosoft.mes.tool.mock.domain.exception;


import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.value.Identifier;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;

@Getter
public class NoPartAvailableException extends ApplicationException {
    protected final Identifier portId;

    public NoPartAvailableException(ToolId toolid, LoadportId portId) {
        super(toolid);
        this.portId = portId;
    }
}
