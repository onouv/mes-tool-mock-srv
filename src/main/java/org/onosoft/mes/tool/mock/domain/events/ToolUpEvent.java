package org.onosoft.mes.tool.mock.domain.events;

import org.onosoft.mes.tool.mock.domain.value.DomainEvent;
import org.onosoft.mes.tool.mock.domain.value.TimeInstant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@org.onosoft.ddd.annotations.DomainEvent
public class ToolUpEvent extends DomainEvent {

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public ToolUpEvent(@JsonProperty("time") TimeInstant timeStamp) {
		super(timeStamp);
	}
	
	public TimeInstant getTimeStamp() {
		return this.timeStamp;
	}
	
	public String toString() {
		return String.format("{ timeStamp: %s }",this.timeStamp.toString());
	}

}
