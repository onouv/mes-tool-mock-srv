package org.onosoft.mes.tool.mock.domain.provided.value;

public enum ToolStates {
    DOWN, UP, STOPPED, IDLE, OPERATING, CHOICE_START
}
/*
public enum ToolStates {
    DOWN("DOWN"),
    UP("UP"),
    STOPPED("STOPPED"),
    CHOICE_START("CHOICE_START"),
    IDLE("IDLE"),
    IDLE_UPSTREAM("IDLE_UPSTREAM"),
    IDLE_DOWNSTREAM("IDLE_DOWNSTREAM"),
    CHOICE_IDLE("CHOICE_IDLE"),
    OPERATING("OPERATING"),
    OPERATING_END("OPERATING_END"),
    CHOICE_LOADING("CHOICE_LOADING"),
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
*/