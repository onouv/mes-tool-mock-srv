package org.onosoft.mes.tool.mock.domain.tool.state;

import lombok.NoArgsConstructor;
import org.onosoft.mes.tool.mock.domain.tool.AbstractTool;

@NoArgsConstructor
public class IdleState extends ToolState {

    protected IdleState instance = new IdleState();

    @Override
    public void triggerStateTransition(AbstractTool context) {

    }
}
