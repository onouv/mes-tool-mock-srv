package org.onosoft.mes.tool.mock.domain.event;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@org.onosoft.ddd.annotations.DomainEvent
@Getter
public class ToolUpEvent extends ToolEvent {

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public ToolUpEvent(
			@JsonProperty("toolId") String toolId) {
		super(toolId);
	}

	public String toString() {
		return String.format(
				"Tool %s UP at %s.", this.toolId, this.timeStamp);
	}

}
