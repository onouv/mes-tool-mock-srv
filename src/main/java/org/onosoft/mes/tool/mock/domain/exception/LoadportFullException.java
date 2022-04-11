package org.onosoft.mes.tool.mock.domain.exception;

import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.value.Identifier;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;

@Getter

public class LoadportFullException extends ApplicationException {
    protected final LoadportId portId;
    protected final Part rejectedPart;

    public LoadportFullException(LoadportId portId, Part rejectedPart) {

    }
}
