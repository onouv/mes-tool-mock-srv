package org.onosoft.mes.tool.mock.domain.event;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;

@org.onosoft.ddd.annotations.DomainEvent
@Getter
public class ToolUpEvent extends ToolEvent {

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public ToolUpEvent(
			@JsonProperty("toolId") ToolId toolId) {
		super(toolId);
	}

	public String toString() {
		return String.format(
				"Tool %s UP at %s.", this.toolId, this.timeStamp);
	}

}
