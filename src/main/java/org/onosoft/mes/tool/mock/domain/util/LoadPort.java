package org.onosoft.mes.tool.mock.domain.util;

import org.onosoft.mes.tool.mock.domain.provided.Part;
import java.util.concurrent.LinkedBlockingQueue;

public class LoadPort {

    protected final LinkedBlockingQueue<Part> buffer;
    protected final String id;

    public LoadPort(String portId, int bufferLength) {
        this.id = portId;
        this.buffer = new LinkedBlockingQueue<>(bufferLength);
    }

    public boolean load(Part part) throws IllegalArgumentException {
        if(part == null)
            throw new IllegalArgumentException("parameter part must not be null");

        return this.buffer.offer(part);
    }

    public Part next() {
        return this.buffer.poll();
    }

    public String getId() {
        return this.id;
    }
}
