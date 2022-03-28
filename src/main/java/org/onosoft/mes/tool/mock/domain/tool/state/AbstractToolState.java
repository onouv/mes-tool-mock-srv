package org.onosoft.mes.tool.mock.domain.tool.state;

import org.onosoft.mes.tool.mock.domain.provided.Tool;

public interface AbstractToolState {

    public void triggerStateTransition(Tool context);
}
