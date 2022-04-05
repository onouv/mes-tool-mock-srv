package org.onosoft.mes.tool.mock.domain.tool;

import lombok.Getter;
import org.onosoft.ddd.annotations.AggregateRoot;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;

@AggregateRoot
@Getter
public class DefaultTool implements Tool {

    protected String id;
    protected static final int WIP_CAPACITY_DEFAULT = 11;
    protected static final int WIP_PROCESS = 1;

    protected final LoadPort outport;
    protected final LoadPort inport;

    @Autowired
    StateMachine<String, String> stateMachine;


    public DefaultTool(String id) {
        this.id = id;
        this.inport = new LoadPort("out.1", WIP_CAPACITY_DEFAULT - WIP_PROCESS);
        this.outport = new LoadPort("out.1", WIP_CAPACITY_DEFAULT - WIP_PROCESS);
    }

    public DefaultTool(String id, int wipCapacity) {
        this.id = id;
        if (wipCapacity < 0) {
            throw new IllegalArgumentException("wipCapacity must be positive number.");
        }
        this.inport = new LoadPort("out.1", wipCapacity - WIP_PROCESS);
        this.outport = new LoadPort("out.1", wipCapacity - WIP_PROCESS);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop(IdleReason reason) {
    }

    @Override
    public void loadPart(Part part) throws LoadportFullException {
        if(!this.inport.load(part))
        {
            throw new LoadportFullException(this.id, inport.getId(), part);
        }
    }

    @Override
    public Part unloadPart() throws NoPartAvailableException {
        Part unloaded = this.outport.next();
        if(unloaded == null)
            throw new NoPartAvailableException(this.id, this.outport.getId());
        return unloaded;
    }

    @Override
    public void breakDown() {

    }

    @Override
    public void repair() {

    }


}
