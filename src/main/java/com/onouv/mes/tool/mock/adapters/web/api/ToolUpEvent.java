package com.onouv.mes.tool.mock.adapters.web.api;

import com.onouv.mes.domain.TimeInstant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ToolUpEvent {
	
	protected final TimeInstant timeStamp;
	
	
	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public ToolUpEvent(@JsonProperty("time") TimeInstant timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public TimeInstant getTimeStamp() {
		return this.timeStamp;
	}
	
	public String toString() {
		return String.format("{ timeStamp: %s }",this.timeStamp.toString());
	}

}
