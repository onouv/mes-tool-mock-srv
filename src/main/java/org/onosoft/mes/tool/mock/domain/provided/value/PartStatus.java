package org.onosoft.mes.tool.mock.domain.provided.value;

public enum PartStatus {
    UNPROCESSED("UNPROCESSED"),
    PROCESSED_GOOD("PROCESSED_GOOD"),
    PROCESSED_BAD("PROCESSED_BAD");

    public final String value;

    PartStatus(String value) {
        this.value = value;
    }
}
