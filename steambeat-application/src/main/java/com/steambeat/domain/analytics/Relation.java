package com.steambeat.domain.analytics;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.Repositories;
import org.joda.time.DateTime;

import java.util.UUID;

public class Relation extends BaseEntity {

    // do not delete constructor for mongolink
    public Relation() {
    }

    public Relation(final Subject from, final Subject to) {
        this.id = UUID.randomUUID();
        fromId = from.getId();
        toId = to.getId();
        this.creationDate = new DateTime();
        this.weight = 1.0;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public Subject getLeft() {
        return Repositories.subjects().get(fromId);
    }

    public Subject getRight() {
        return Repositories.subjects().get(toId);
    }

    public UUID getFromId() {
        return fromId;
    }

    public UUID getToId() {
        return toId;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(final double weight) {
        this.weight = weight;
    }

    public void addWeight(final double additionalWeight) {
        this.weight += additionalWeight;
    }

    private double weight;
    private UUID fromId;
    private UUID toId;
    private UUID id;
    private DateTime creationDate;
}
