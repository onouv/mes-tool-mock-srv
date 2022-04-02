package org.onosoft.mes.tool.mock.domain.provided.value;

import org.onosoft.ddd.annotations.ValueObject;

@ValueObject
public enum DowntimeReason {
	UNKNOWN("UNKNOWN", "Unknown reason"),
	OPERATOR("OPERATOR", "Operator interaction"),
	UPSTREAM("UPSTREAM", "Lack of material for product"),
	DOWNSTREAM("DOWNSREAM", "product cannot flow downstream/ out buffer full"),
	AUXILIARY("AUXILIARY", "Lack of auxiliary consumable or resource"),
	PROCESS("PROCESS", "Related to production process "),
	FAULT("FAULT", "Equipment fault of tool");

	public final String value;
	public final String description;
	
	DowntimeReason(String val, String desc) {
		this.value = val;
		this.description = desc;
	}
	
	public String toString() {
		return this.value;
	}
}
