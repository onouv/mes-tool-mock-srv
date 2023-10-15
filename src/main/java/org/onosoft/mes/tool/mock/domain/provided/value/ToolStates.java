package org.onosoft.mes.tool.mock.domain.provided.value;


public enum ToolStates {
    DOWN("DOWN"),
    UP("UP"),
    STOPPED("STOPPED"),
    UP_CHOICE("UP_CHOICE"),
    IDLE("IDLE"),
    IDLE_CHOICE("IDLE_CHOICE"),
    IDLE_END("IDLE_END"),
    BLOCKED_UPSTREAM("BLOCKED_UPSTREAM"),
    BLOCKED_DOWNSTREAM("BLOCKED_DOWNSTREAM"),
    BLOCKED_UP_AND_DOWNSTREAM("BLOCKED_UP_AND_DOWNSTREAM"),
    OPERATING("OPERATING"),
    OPERATING_END("OPERATING_END"),
    OPERATING_CHOICE("OPERATING_CHOICE"),
    LOADING_PROCESS("LOADING_PROCESS"),
    PROCESSING_PART("PROCESSING_PART"),
    UNLOADING_PROCESS("UNLOADING_PROCESS"),
    UNLOADING_CHOICE("UNLOADING_CHOICE");

    public final String value;

    ToolStates(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
