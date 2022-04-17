package org.onosoft.mes.tool.mock.domain.provided;


import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.onosoft.ddd.annotations.Entity;
import org.onosoft.mes.tool.mock.domain.provided.value.PartId;
import org.onosoft.mes.tool.mock.domain.provided.value.PartStatus;
import org.onosoft.mes.tool.mock.domain.provided.value.PartType;

@Entity
@Getter
@SuperBuilder
public abstract class Part { //extends PartDto {

    protected PartId id;
    protected PartType type;
    protected PartStatus status;

    public abstract PartStatus processSuccessful();
    public abstract PartStatus processWithFailure();
}
