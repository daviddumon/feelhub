package com.steambeat.domain.illustration;

import com.steambeat.domain.BaseEntity;
import org.joda.time.DateTime;

import java.util.UUID;

public class Illustration extends BaseEntity {

    //do not delete mongolink constructor
    public Illustration() {
    }

    public Illustration(final UUID referenceId, final String link) {
        this.id = UUID.randomUUID();
        this.referenceId = referenceId;
        this.link = link;
        this.creationDate = new DateTime();
        this.lastModificationDate = this.creationDate;
    }

    @Override
    public Object getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public DateTime getLastModificationDate() {
        return lastModificationDate;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    private UUID id;
    private String link;
    private DateTime creationDate;
    private DateTime lastModificationDate;
    private UUID referenceId;
}
