package org.onosoft.mes.tool.mock.domain.tool;

import org.onosoft.ddd.annotations.AggregateRoot;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.ToolInputBufferFullException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.DownTimeReason;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStatus;
import java.util.concurrent.LinkedBlockingQueue;

@AggregateRoot
public class ToolImpl extends Tool {

    private static final int WIP_CAPACITY_DEFAULT = 10;
    private final LinkedBlockingQueue<Part> process;

    private ToolImpl () {
        this.process = null;
    }

    public ToolImpl(String id) {
        super(id);
        this.process = new LinkedBlockingQueue<>(WIP_CAPACITY_DEFAULT);
    }

    public ToolImpl(String id, int wipCapacity) {
        super(id);

        if (wipCapacity < 0) {
            throw new IllegalArgumentException("wipCapacity must be positive number.");
        }

        this.process = new LinkedBlockingQueue<>(wipCapacity);
    }

    @Override
    public void start() {
        if (this.status == ToolStatus.DOWN) {
            this.status = ToolStatus.UP;

            // TODO: fire domain event...
        }
    }

    @Override
    public void stop(DownTimeReason reason) {
        if(this.status == ToolStatus.UP) {
            this.status = ToolStatus.DOWN;
            this.downTimeReason = reason;

            // TODO: fire domain event...
        }
    }

    @Override
    public void loadPart(Part part) throws ToolInputBufferFullException {

        if(this.process.offer(part)) {

            // TODO: fire domain event, return success state of that operation ...

        } else {
            throw new ToolInputBufferFullException(this, part);
        }

    }

    @Override
    public Part unloadPart() throws NoPartAvailableException {

        Part part = this.process.poll();
        if(part != null) {

            // TODO : fire domain event, return success state of that operation ...
            return part;
        } else {
            throw new NoPartAvailableException(this);
        }
    }

    @Override
    public void breakDown() {

    }

    @Override
    public void repair() {

    }
}
