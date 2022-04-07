package org.onosoft.mes.tool.mock.domain.provided.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter @AllArgsConstructor
public abstract class Identifier {

  protected String id;

  @Override
  public boolean equals(Object o) {
    if(o == null)
      return false;

    if(!(o instanceof Identifier))
      return false;

    Identifier c = (Identifier) o;

    return Objects.equals(this.id, c.id);
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 37 * id.hashCode() + result;
    return result;
  }

}
