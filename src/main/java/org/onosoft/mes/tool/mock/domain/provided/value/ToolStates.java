package org.onosoft.mes.tool.mock.domain.provided.value;

public enum ToolStates {
    DOWN("DOWN"),
    UP("UP"),
    STOPPED("STOPPED"),
    IDLE("IDLE"),
    PROCESSING("PROCESSING");

    public final String value;

    ToolStates(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
