package com.steambeat.domain.alchemy;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.keyword.Keyword;

import java.util.UUID;

public class AlchemyAnalysis extends BaseEntity {

    //mongolink constructor
    protected AlchemyAnalysis() {
    }

    public AlchemyAnalysis(final Keyword keyword) {
        this.id = UUID.randomUUID();
        this.referenceId = keyword.getReferenceId();
        this.value = keyword.getValue();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public String getValue() {
        return value;
    }

    private UUID referenceId;
    private UUID id;
    private String value;
}
