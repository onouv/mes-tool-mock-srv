package org.onosoft.mes.tool.mock.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.onosoft.mes.tool.mock.domain.value.TimeInstant;

@AllArgsConstructor
public class DomainEvent {

    @JsonProperty("time")
    protected final TimeInstant timeStamp;

    protected DomainEvent() {
        this.timeStamp = new TimeInstant();
    }
}
