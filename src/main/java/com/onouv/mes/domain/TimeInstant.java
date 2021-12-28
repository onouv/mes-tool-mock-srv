package com.onouv.mes.domain;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeInstant {
	
	private final String utcInstant;
	private final String pattern = "YYYY-MM-DDTHH.mm.ss.SSSZ";
	
	public TimeInstant(String utcInstant) {
		this.utcInstant = utcInstant;
	}
	
	public ZonedDateTime getUtcInstant() {
		
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern(this.pattern)
				.withZone(ZoneOffset.UTC);
		ZonedDateTime zdt = ZonedDateTime.parse(this.utcInstant, formatter);
		return zdt;
	}
	
	public String toString() {
		return this.utcInstant.toString();
	}
}