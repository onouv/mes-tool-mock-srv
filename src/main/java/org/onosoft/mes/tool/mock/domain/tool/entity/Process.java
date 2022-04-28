package org.onosoft.mes.tool.mock.domain.tool.entity;


import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.value.ProcessId;

@Getter
public class Process {
    ProcessId id;
    protected Part processingPart;

    public Process() {
        this.id = new ProcessId("DEFAULT_PROCESS");
        this.processingPart = null;
    }

    public void run(Part part) {
        this.processingPart = part;
    }

    public Part eject() {
        Part p = this.processingPart;
        this.processingPart = null;
        return p;
    }
}
