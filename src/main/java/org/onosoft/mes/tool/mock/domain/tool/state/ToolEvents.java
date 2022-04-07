package org.onosoft.mes.tool.mock.domain.tool.state;
public enum ToolEvents {
    FAULT,
    FAULT_CLEARED,
    FINISHED,
    PART_LOADING,
    PART_UNLOADING,
    START,
    STOP
}
/*
public enum ToolEvents {
    FAULT("FAULT"),
    FAULT_CLEARED("FAULT_CLEARED"),
    FINISHED("FINISHED"),
    PART_LOADING("PART_LOADING"),
    PART_UNLOADING("PART_UNLOADING"),
    START("START"),
    STOP("STOP");

    private final String event;

    ToolEvents(String event) {
        this.event = event;
    }

}
*/