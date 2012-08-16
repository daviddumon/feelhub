package com.steambeat.domain.relation;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;
import org.joda.time.DateTime;

import java.util.UUID;

public class Relation extends BaseEntity {

    // do not delete constructor for mongolink
    public Relation() {
    }

    public Relation(final Reference from, final Reference to, final double weight) {
        this.id = UUID.randomUUID();
        fromId = from.getId();
        toId = to.getId();
        this.creationDate = new DateTime();
        this.weight = weight;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public Reference getLeft() {
        return Repositories.references().get(fromId);
    }

    public Reference getRight() {
        return Repositories.references().get(toId);
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
