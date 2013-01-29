package com.feelhub.domain.media;

import com.feelhub.domain.BaseEntity;

import java.util.UUID;

public class Media extends BaseEntity {

    //mongolink
    protected Media() {
    }

    public Media(final UUID fromId, final UUID toId) {
        this.id = UUID.randomUUID();
        this.fromId = fromId;
        this.toId = toId;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UUID getFromId() {
        return fromId;
    }

    public UUID getToId() {
        return toId;
    }

    public void setFromId(final UUID fromId) {
        this.fromId = fromId;
    }

    public void setToId(final UUID toId) {
        this.toId = toId;
    }

    private UUID fromId;
    private UUID toId;
    private UUID id;
}
