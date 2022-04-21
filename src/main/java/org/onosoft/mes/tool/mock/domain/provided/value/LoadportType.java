package org.onosoft.mes.tool.mock.domain.provided.value;


public enum LoadportType {

  INPORT("INPORT"),
  OUTPORT("OUTPORT");

  public final String value;

  LoadportType(String val) {
    this.value = val;
  }

  public String toString() {
      return this.value;
    }

}
