package org.onosoft.mes.tool.mock.domain.tool.entity;

import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.provided.value.Identifier;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportType;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.tool.state.guard.PortStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class LoadPort implements PortStatus {

    protected final LinkedBlockingQueue<Part> buffer;
    protected final ToolId toolId;
    protected final LoadportId id;
    protected final LoadportType type;
    protected final int capacity;

    public LoadPort(ToolId toolId, LoadportId portId, LoadportType type, int capacity) throws IllegalArgumentException {
        if(capacity <= 0)
            throw new IllegalArgumentException(String
                .format("capacity must be null or positive, but was given as %d", capacity));
        this.toolId = toolId;
        this.id = portId;
        this.buffer = new LinkedBlockingQueue<>(capacity);
        this.capacity = capacity;
        this.type = type;
    }

    public void load(Part part) throws IllegalArgumentException, LoadportFullException {
        if(part == null)
            throw new IllegalArgumentException("parameter part must not be null");

        if( ! this.buffer.offer(part))
            throw new LoadportFullException(this.toolId, this.id, part);
    }

    public Part next() throws NoPartAvailableException {
        Part part = this.buffer.poll();

        if(part == null)
            throw new NoPartAvailableException(this.toolId, this.id);

        return part;
    }

    public Identifier getId() {
        return this.id;
    }
    public LoadportType getType() { return this.type; }

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

    public List<Part> getParts() {
        return new ArrayList<>(this.buffer);
    }

}
