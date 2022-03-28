package org.onosoft.mes.tool.mock.domain.tool.value;

import org.onosoft.ddd.annotations.ValueObject;

@ValueObject
public enum DownTimeReason {
	UNKNOWN("UNKNOWN", "Unknown reason"),
	OPERATOR("OPERATOR", "Operator interaction"),
	MATERIAL_MISSING("MATERIAL", "Lack of material for product"),
	AUXILIARY_MISSING("AUXILIARY", "Lack of auxiliary consumable or resource"),
	PROCESS("PROCESS", "Related to production process "),
	TOOL("TOOL", "Issued by tool (equiment or machine)");	
	
	public final String value;
	public final String description;
	
	DownTimeReason(String val, String desc) {
		this.value = val;
		this.description = desc;
	}
	
	public String toString() {
		return this.value;
	}
}
