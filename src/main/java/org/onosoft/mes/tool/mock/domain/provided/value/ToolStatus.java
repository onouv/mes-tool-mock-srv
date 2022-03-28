package org.onosoft.mes.tool.mock.domain.provided.value;

public enum ToolStatus {
    UP("UP"),
    DOWN("DOWN");

    public final String value;

    ToolStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
