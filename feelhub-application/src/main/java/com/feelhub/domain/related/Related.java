package com.feelhub.domain.related;

import com.feelhub.domain.BaseEntity;

import java.util.UUID;

public class Related extends BaseEntity {

    // do not delete constructor for mongolink
    public Related() {
    }

    public Related(final UUID fromId, final UUID toId, final double weight) {
        this.id = UUID.randomUUID();
        this.fromId = fromId;
        this.toId = toId;
        this.weight = weight;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(final double weight) {
        this.weight = weight;
    }

    public void addWeight(final double additionalWeight) {
        this.weight += additionalWeight;
    }

    public void setFromId(final UUID fromId) {
        this.fromId = fromId;
    }

    public void setToId(final UUID toId) {
        this.toId = toId;
    }

    private double weight;
    private UUID fromId;
    private UUID toId;
    private UUID id;
}
