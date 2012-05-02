package com.steambeat.domain.association;

import com.steambeat.domain.BaseEntity;

import java.util.UUID;

public class Association extends BaseEntity {

    // Mongolink constructor
    protected Association() {
    }

    public Association(final Identifier identifier, final UUID uuid) {
        this.id = identifier.toString();
        this.subjectId = uuid;
    }

    @Override
    public String getId() {
        return id;
    }

    public UUID getSubjectId() {
        return subjectId;
    }

    private String id;
    private UUID subjectId;
}
