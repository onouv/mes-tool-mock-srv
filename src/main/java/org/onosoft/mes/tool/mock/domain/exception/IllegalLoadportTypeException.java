package org.onosoft.mes.tool.mock.domain.exception;

import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;

@Getter
public class IllegalLoadportTypeException extends ApplicationException {
  LoadportId loadportId;
  public IllegalLoadportTypeException(ToolId toolId, LoadportId loadportId) {
    super(toolId);
    this.loadportId = loadportId;
  }
}
