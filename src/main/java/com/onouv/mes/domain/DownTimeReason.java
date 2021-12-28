package com.onouv.mes.domain;

public enum DownTimeReason {
	UNKNOWN("UNKNOWN", "Unknown reason"),
	OPERATOR("OPERATOR", "Operator interaction"),
	MATERIAL("MATERIAL", "Lack of material for product"),
	AUXILIARY("AUXILIARY", "Lack of auxiliary consumable or resource"),
	PROCESS("PROCESS", "Related to production process "),
	TOOL("TOOL", "Issued by tool (equiment or machine)");	
	
	public final String value;
	public final String description;
	
	private DownTimeReason(String val, String desc) {
		this.value = val;
		this.description = desc;
	}
	
	public String toString() {
		return this.value;
	}
}
