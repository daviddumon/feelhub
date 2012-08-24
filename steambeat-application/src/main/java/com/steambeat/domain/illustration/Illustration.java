package com.steambeat.domain.illustration;

import com.steambeat.domain.BaseEntity;

import java.util.UUID;

public class Illustration extends BaseEntity {

    //do not delete mongolink constructor
    public Illustration() {
    }

    public Illustration(final UUID referenceId, final String link) {
        this.id = UUID.randomUUID();
        this.referenceId = referenceId;
        this.link = link;
    }

    @Override
    public Object getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(final UUID referenceId) {
        this.referenceId = referenceId;
    }

    private UUID id;
    private String link;
    private UUID referenceId;
}
