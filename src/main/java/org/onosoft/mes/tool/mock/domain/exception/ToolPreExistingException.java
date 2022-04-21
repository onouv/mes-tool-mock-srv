package org.onosoft.mes.tool.mock.domain.exception;

import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;

@Getter
public class ToolPreExistingException extends ApplicationException {
  public ToolPreExistingException(ToolId id) {
    super(id);
  }
}
