package org.onosoft.mes.tool.mock.domain.tool.entity;

import org.onosoft.mes.tool.mock.domain.provided.Part;

public class Process {
    public Part run(Part part) {
        System.out.println("processing part" + part.toString());
        return part;
    }
}
