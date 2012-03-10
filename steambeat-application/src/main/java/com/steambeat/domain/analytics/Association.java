package com.steambeat.domain.analytics;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.analytics.identifiers.Identifier;

import java.util.UUID;

public class Association extends BaseEntity {

    // Mongolink constructor
    protected Association() {
    }

    public Association(final Identifier identifier, final UUID uuid) {
        this.id = identifier.toString();
        this.subjectId = uuid.toString();
    }

    @Override
    public String getId() {
        return id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    private String id;
    private String subjectId;
}
