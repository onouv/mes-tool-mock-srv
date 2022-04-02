package org.onosoft.mes.tool.mock.domain.tool.state.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.Part;

@Getter
@AllArgsConstructor
public class FINISHED implements FSMEvent {
    protected Part part;
}
