package org.onosoft.mes.tool.mock.domain.tool.state.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FAULT implements FSMEvent {
    protected String message;
}
