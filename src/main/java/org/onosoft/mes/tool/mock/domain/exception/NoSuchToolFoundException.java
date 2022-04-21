package org.onosoft.mes.tool.mock.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;

@Getter
public class NoSuchToolFoundException extends ApplicationException {

  public NoSuchToolFoundException(ToolId toolId) {
    super(toolId);
  }
}
