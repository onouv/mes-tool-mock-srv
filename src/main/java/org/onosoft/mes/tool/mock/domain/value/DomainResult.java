package org.onosoft.mes.tool.mock.domain.value;

import lombok.Builder;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import java.util.List;

@Getter
@Builder
public class DomainResult {
  protected final Tool tool;
  protected final List<DomainEvent> events;
  protected final ApplicationException applicationException;

  public String toString() {
    StringBuilder b = new StringBuilder(String.format("\ttool: %s", tool));
    b.append("\n\tevents: ");

    for (DomainEvent e : events) {
      b.append("\n\t\t");
      b.append(e.toString());
      b.append(",");
    }

    b.append("\n\tapplicationException:");
    if(applicationException != null) {
      b.append(applicationException);
    } else {
      b.append("none");
    }

    return b.toString();
  }
}
