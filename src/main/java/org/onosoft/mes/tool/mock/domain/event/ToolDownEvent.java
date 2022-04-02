package org.onosoft.mes.tool.mock.domain.event;

import org.onosoft.mes.tool.mock.domain.provided.value.DowntimeReason;
import org.onosoft.mes.tool.mock.domain.value.DomainEvent;
import org.onosoft.mes.tool.mock.domain.value.TimeInstant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@org.onosoft.ddd.annotations.DomainEvent
public class ToolDownEvent extends DomainEvent {
	
	private final DowntimeReason reason;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public ToolDownEvent(
			@JsonProperty("time") TimeInstant timeStamp,
			@JsonProperty("reason") DowntimeReason reason) {
		super(timeStamp);
		this.reason = reason;
	}
	
	public DowntimeReason getReason() {
		return this.reason;
	}
	
	public String toString() {
		return String.format(
				"{ timeStamp: %s, reason: %s }",
				this.timeStamp.toString(),
				this.reason.toString());
	}
}
