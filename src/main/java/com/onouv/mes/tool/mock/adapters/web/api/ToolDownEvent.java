package com.onouv.mes.tool.mock.adapters.web.api;

import com.onouv.mes.domain.DownTimeReason;
import com.onouv.mes.domain.TimeInstant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ToolDownEvent extends ToolUpEvent {
	
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
