package com.feelhub.domain.relation;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class Relation extends BaseEntity {

    // do not delete constructor for mongolink
    public Relation() {
    }

    public Relation(final Topic from, final Topic to, final double weight) {
        this.id = UUID.randomUUID();
        fromId = from.getId();
        toId = to.getId();
        this.weight = weight;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public Topic getFrom() {
        return Repositories.topics().get(fromId);
    }

    public Topic getTo() {
        return Repositories.topics().get(toId);
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
