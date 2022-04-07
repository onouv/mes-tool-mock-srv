package org.onosoft.mes.tool.mock.domain.tool.entity;

import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.value.Identifier;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.PortStatus;

import java.util.concurrent.LinkedBlockingQueue;

public class LoadPort implements PortStatus {

    protected final LinkedBlockingQueue<Part> buffer;
    protected final Identifier id;
    protected final int capacity;

    public LoadPort(Identifier portId, int capacity) throws IllegalArgumentException {
        if(capacity <= 0)
            throw new IllegalArgumentException(String
                .format("capacity must be null or positive, but was given as %i", capacity));
        this.id = portId;
        this.buffer = new LinkedBlockingQueue<>(capacity);
        this.capacity = capacity;
    }

    public boolean load(Part part) throws IllegalArgumentException {
        if(part == null)
            throw new IllegalArgumentException("parameter part must not be null");

        return this.buffer.offer(part);
    }

    public Part next() {
        return this.buffer.poll();
    }

    public Identifier getId() {
        return this.id;
    }

    @Override
    public boolean isEmpty() {
        return this.buffer.isEmpty();
    }

    @Override
    public boolean isFull() {
        return this.buffer.size() == this.capacity();
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

}
