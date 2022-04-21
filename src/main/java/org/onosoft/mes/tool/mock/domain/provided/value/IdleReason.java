package org.onosoft.mes.tool.mock.domain.provided.value;

public enum IdleReason {
	UPSTREAM("UPSTREAM"),
	DOWNSTREAM("DOWNSTREAM");
	// AUXILIARY("AUXILIARY", "Lack of auxiliary consumable or resource");

	public final String value;
	
	IdleReason(String val) {
		this.value = val;
	}
	
	public String toString() {
		return this.value;
	}
}
