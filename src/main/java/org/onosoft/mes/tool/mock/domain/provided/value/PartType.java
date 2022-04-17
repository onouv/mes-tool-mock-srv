package org.onosoft.mes.tool.mock.domain.provided.value;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PartType {
  protected final String id;
  protected final String description;
  protected final String parentid;
/*
  protected PartType(Builder b) {
    this.id = b.id;
    this.description = b.description;
    this.parentid = b.parentid;
  }

  public static class Builder {
    protected String id;
    protected String description;
    protected String parentid;

    public Builder() {}

    public Builder withId(String id) {
      this.id = id;
      return this;
    }

    public Builder withDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder withParent(PartType parent) {
      this.parentid = parent.id;
    }
  } */
}
