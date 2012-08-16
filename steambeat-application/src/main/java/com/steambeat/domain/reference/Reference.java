package com.steambeat.domain.reference;

import com.steambeat.domain.BaseEntity;
import org.joda.time.DateTime;

import java.util.UUID;

public class Reference extends BaseEntity {

    //mongolink constructor do not delete!
    protected Reference() {
    }

    public Reference(final UUID id) {
        this.id = id;
        this.creationDate = new DateTime();
        this.lastModificationDate = this.creationDate;
        this.active = true;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public boolean isActive() {
        return active;
    }

    public DateTime getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(final DateTime modificationDate) {
        this.lastModificationDate = modificationDate;
    }

    private UUID id;
    private DateTime creationDate;
    private boolean active;
    private DateTime lastModificationDate;
}
