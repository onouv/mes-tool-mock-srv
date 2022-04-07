package org.onosoft.mes.tool.mock.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.value.Identifier;

@Getter
@AllArgsConstructor
public class LoadportFullException extends Exception {
    protected final Identifier toolId;
    protected final Identifier portId;
    protected final Part rejectedPart;
}
