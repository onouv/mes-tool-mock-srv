package org.onosoft.mes.tool.mock.domain.tool.state;

import org.onosoft.mes.tool.mock.domain.provided.Tool;

public interface StateContext {

    public Tool getTool();
    public void changeStateTo(ToolState newState) throws NullPointerException;
}
