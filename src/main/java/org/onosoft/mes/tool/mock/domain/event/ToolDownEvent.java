package org.onosoft.mes.tool.mock.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@org.onosoft.ddd.annotations.DomainEvent
public class ToolDownEvent extends ToolEvent {

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public ToolDownEvent(
			@JsonProperty("tool") String toolId) {
		super(toolId);

	}
	
	public String toString() {
		return String.format(
				"Tool %s DOWN at %s.", this.toolId, this.timeStamp);
	}
}
