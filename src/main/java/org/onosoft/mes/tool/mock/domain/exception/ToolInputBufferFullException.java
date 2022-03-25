package org.onosoft.mes.tool.mock.domain.exception;

import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;

@Getter
public class ToolInputBufferFullException extends Exception {
    private final Tool tool;
    private final Part rejectedPart;

    public ToolInputBufferFullException(Tool tool, Part part) {
        this.tool = tool;
        this.rejectedPart = part;
    }
}
