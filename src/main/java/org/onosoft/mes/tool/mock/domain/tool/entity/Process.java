package org.onosoft.mes.tool.mock.domain.tool.entity;


import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.Part;

@Getter
public class Process {
    protected Part processedPart;

    public Process() {
        this.processedPart = null;
    }

    public void run(Part part) {
        System.out.println("processing part" + part.toString());
        this.processedPart = part;
    }
}
