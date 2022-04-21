package org.onosoft.mes.tool.mock.adapters.in.web.status.dto;

import lombok.Builder;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;

import java.util.Collection;

@Getter
@Builder
public class StatusDto {
  Collection<ToolStates> states;

  /*
  public enum MajorState {
    DOWN("DOWN"),
    UP("UP");
    private String value;
    private MajorState(String val) {
      this.value = val;
    }
    public String toString() {
      return this.value;
    }
  }
  public enum MinorState {
    STOPPED("STOPPED"),
    IDLE("IDLE"),
    PROCESSING("PROCESSING");
    private String value;
    private MinorState(String val) {
      this.value = val;
    }
    public String toString() {
      return this.value;
    }
  }
  private MajorState major;
  private MinorState minor;

   */
}
