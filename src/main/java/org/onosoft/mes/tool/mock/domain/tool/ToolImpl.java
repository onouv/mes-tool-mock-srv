package org.onosoft.mes.tool.mock.domain.tool;

import lombok.Getter;
import org.onosoft.ddd.annotations.AggregateRoot;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.ToolInputBufferFullException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.DownTimeReason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;

import java.util.concurrent.LinkedBlockingQueue;

@AggregateRoot
@Getter
public class ToolImpl implements Tool {

    protected String id;
    protected static final int WIP_CAPACITY_DEFAULT = 10;
    protected final LinkedBlockingQueue<Part> process;

    @Autowired
    StateMachine<String, String> stateMachine;


    public ToolImpl(String id) {
        this.id = id;
        this.process = new LinkedBlockingQueue<>(WIP_CAPACITY_DEFAULT);
    }

    public ToolImpl(String id, int wipCapacity) {
        if (wipCapacity < 0) {
            throw new IllegalArgumentException("wipCapacity must be positive number.");
        }
        this.id = id;

        this.process = new LinkedBlockingQueue<>(wipCapacity);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop(DownTimeReason reason) {
    }

    @Override
    public void loadPart(Part part) throws ToolInputBufferFullException {
    }

    @Override
    public Part unloadPart() throws NoPartAvailableException {
    }

    @Override
    public void breakDown() {

    }

    @Override
    public void repair() {

    }


}
