package org.onosoft.mes.tool.mock.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.Tool;

@Getter
@AllArgsConstructor
public class NoPartAvailableException extends Exception {
    protected final String toolId;
    protected final String portId;
}
