package org.onosoft.mes.tool.mock.domain.tool.state;

import org.onosoft.mes.tool.mock.domain.provided.Tool;

public class StoppedState implements AbstractToolState {

    private StoppedState instance = new StoppedState();

    public void triggerStateTransition(Tool context) {

    }
}
