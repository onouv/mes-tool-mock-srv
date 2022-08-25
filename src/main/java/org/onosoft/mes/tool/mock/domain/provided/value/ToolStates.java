package org.onosoft.mes.tool.mock.domain.provided.value;

public enum ToolStates {
    BASE("BASE"),
    DOWN("DOWN"),
    UP("UP"),
    STOPPED("STOPPED"),
    IDLE("IDLE"),
    OPERATING("OPERATING"),
    LOADING_PROCESS("LOADING_PROCESS"),
    PROCESSING_PART("PROCESSING_PART"),
    UNLOADING_PROCESS("UNLOADING_PROCESS");

    public final String value;

    ToolStates(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
