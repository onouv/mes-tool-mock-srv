package org.onosoft.mes.tool.mock.domain.tool;

import lombok.Getter;
import org.onosoft.ddd.annotations.AggregateRoot;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.ToolInputBufferFullException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.DownTimeReason;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStatus;
import org.onosoft.mes.tool.mock.domain.tool.state.ToolState;
import org.onosoft.mes.tool.mock.domain.tool.state.StateContext;
import org.onosoft.mes.tool.mock.domain.tool.state.StoppedState;

import java.util.concurrent.LinkedBlockingQueue;

@AggregateRoot
@Getter
public class ToolImpl implements Tool, StateContext {

    protected String id;
    protected static final int WIP_CAPACITY_DEFAULT = 10;
    protected final LinkedBlockingQueue<Part> process;
    protected ToolState currentState;

    public ToolImpl(String id) {
        this.id = id;
        this.currentState = StoppedState.instance();
        this.process = new LinkedBlockingQueue<>(WIP_CAPACITY_DEFAULT);
    }

    public ToolImpl(String id, int wipCapacity) {
        if (wipCapacity < 0) {
            throw new IllegalArgumentException("wipCapacity must be positive number.");
        }
        this.id = id;
        this.currentState = StoppedState.instance();
        this.process = new LinkedBlockingQueue<>(wipCapacity);
    }

    @Override
    public void start() {

        this.currentState.start(this);
    }

    @Override
    public void stop(DownTimeReason reason) {

        this.currentState.stop(this, reason);
    }

    @Override
    public void loadPart(Part part) throws ToolInputBufferFullException {
        this.currentState.loadPart(this, part);
    }

    @Override
    public Part unloadPart() throws NoPartAvailableException {
        return this.currentState.unloadPart(this);
    }

    @Override
    public void breakDown() {

    }

    @Override
    public void repair() {

    }

    @Override
    public void changeStateTo(ToolState newState) throws NullPointerException {
        if(newState != null) {
            this.currentState = newState;
        } else {
            throw new NullPointerException();
        }
    }
}
