package com.feelhub.domain.reference;

import com.feelhub.domain.BaseEntity;

import java.util.UUID;

public class Reference extends BaseEntity {

    //mongolink constructor do not delete!
    protected Reference() {
    }

    public Reference(final UUID id) {
        this.id = id;
        this.active = true;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public UUID getCurrentReferenceId() {
        return currentReferenceId;
    }

    public void setCurrentReferenceId(final UUID currentId) {
        this.currentReferenceId = currentId;
    }

    private UUID id;
    private boolean active;
    private UUID currentReferenceId;
}
