package org.onosoft.mes.tool.mock.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.Part;

@Getter
@AllArgsConstructor
public class LoadportFullException extends Exception {
    protected final String toolId;
    protected final String portId;
    protected final Part rejectedPart;
}
