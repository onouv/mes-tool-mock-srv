package org.onosoft.mes.tool.mock.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.tool.DefaultTool;

import java.util.List;
import java.util.ListIterator;
/*
@AllArgsConstructor
@Getter
public class EventBundle {

    final DefaultTool tool;
    final List<DomainEvent> events;
    final ApplicationException applicationException;

    @Override
    public String toString() {
        StringBuilder eventsStr = new StringBuilder("events={\n");

        ListIterator<DomainEvent> iter = events.listIterator();
        while (iter.hasNext()) {
            eventsStr.append(iter.next().toString());
            eventsStr.append('\n');
        }
        eventsStr.append('}');
        return String.format("\nEventBundle{ tool=%s,\n%s\n", tool.getId(), eventsStr.toString());
    }
}


 */