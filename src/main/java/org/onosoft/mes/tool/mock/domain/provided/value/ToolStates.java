package org.onosoft.mes.tool.mock.domain.provided.value;

public enum ToolStates {
    DOWN("DOWN"),
    UP("UP"),
    UP_STOPPED("UP_STOPPED"),
    UP_IDLE("UP_IDLE"),
    UP_PROCESSING("UP_PROCESSING");

    public final String value;

    ToolStates(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
