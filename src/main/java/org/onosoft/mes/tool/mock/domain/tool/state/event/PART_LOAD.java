package org.onosoft.mes.tool.mock.domain.tool.state.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.Part;

@Getter
@AllArgsConstructor
public class PART_LOAD implements FSMEvent {
    protected Part part;
}
