package org.onosoft.mes.tool.mock.domain.events;

import org.onosoft.mes.tool.mock.domain.tool.value.DownTimeReason;
import org.onosoft.mes.tool.mock.domain.value.DomainEvent;
import org.onosoft.mes.tool.mock.domain.value.TimeInstant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@org.onosoft.ddd.annotations.DomainEvent
public class ToolDownEvent extends DomainEvent {
	
	private final DownTimeReason reason;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public ToolDownEvent(
			@JsonProperty("time") TimeInstant timeStamp,
			@JsonProperty("reason") DownTimeReason reason) {
		super(timeStamp);
		this.reason = reason;
	}
	
	public DownTimeReason getReason() {
		return this.reason;
	}
	
	public String toString() {
		return String.format(
				"{ timeStamp: %s, reason: %s }",
				this.timeStamp.toString(),
				this.reason.toString());
	}
}
