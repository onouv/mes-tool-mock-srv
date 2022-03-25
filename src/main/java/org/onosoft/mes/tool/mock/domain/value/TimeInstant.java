package org.onosoft.mes.tool.mock.domain.value;

import org.onosoft.ddd.annotations.ValueObject;

import java.sql.Time;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@ValueObject
public class TimeInstant {
	
	private final String utcInstant;
	private final String pattern = "YYYY-MM-DDTHH.mm.ss.SSSZ";

	public TimeInstant() {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern(this.pattern)
				.withZone(ZoneOffset.UTC);
		this.utcInstant = ZonedDateTime.now().format(formatter);
	}
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
		return this.utcInstant;
	}
}