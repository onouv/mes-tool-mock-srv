package org.onosoft.mes.tool.mock.domain.exception;

import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.Tool;

@Getter
public class NoPartAvailableException extends Exception {
    private final Tool tool;

    public NoPartAvailableException(Tool tool) {
        this.tool = tool;
    }
}
