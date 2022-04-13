package org.onosoft.mes.tool.mock.domain.value;

import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolEvents;
import org.springframework.statemachine.state.State;

import java.util.List;


@Getter
public class DomainResult {
  protected final ToolId toolId;
  protected final ToolStates toolState;
  protected final List<DomainEvent> events;
  protected final ApplicationException applicationException;

  protected DomainResult(DomainResultBuilder builder) {
    this.toolId = builder.toolId;
    this.toolState = builder.toolState;
    this.events = builder.events;
    this.applicationException = builder.applicationException;
  }

  public static class DomainResultBuilder {
    protected ToolId toolId;
    protected ToolStates toolState;
    protected List<DomainEvent> events;
    protected ApplicationException applicationException;

    public DomainResultBuilder() {
    }

    public DomainResult build() {
      return new DomainResult(this);
    }
    public DomainResultBuilder withToolId(ToolId toolId) {
      this.toolId = toolId;
      return this;
    }
    public DomainResultBuilder withToolState(State<ToolStates, ToolEvents> toolState) {
      this.toolState = toolState.getId();
      return this;
    }
    public DomainResultBuilder withDomainEvents(List<DomainEvent> events) {
      this.events = events;
      return this;
    }
    public DomainResultBuilder withApplicationException(ApplicationException exception) {
      this.applicationException = exception;
      return this;
    }
  }
}
