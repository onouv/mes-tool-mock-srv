package org.onosoft.mes.tool.mock.domain.tool.state.guard;

public interface PortStatus {
  boolean isFull();
  boolean isEmpty();
  int capacity();
}
