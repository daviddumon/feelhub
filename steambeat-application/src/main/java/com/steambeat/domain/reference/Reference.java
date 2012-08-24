package com.steambeat.domain.reference;

import com.steambeat.domain.BaseEntity;

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

    private UUID id;
    private boolean active;
}
