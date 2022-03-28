package org.onosoft.mes.tool.mock.domain.provided;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onosoft.ddd.annotations.Entity;
import org.onosoft.mes.tool.mock.domain.provided.value.PartStatus;

@Entity
@Getter
@AllArgsConstructor
public abstract class Part {

    private final String partId;
    private PartStatus status;

    public abstract PartStatus processSuccessful();
    public abstract PartStatus processWithFailure();
}
