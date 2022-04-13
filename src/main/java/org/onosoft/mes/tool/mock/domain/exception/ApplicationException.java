package org.onosoft.mes.tool.mock.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.value.Identifier;

@Getter
@AllArgsConstructor
public abstract class ApplicationException extends Exception {
  protected final Identifier toolId;
}
