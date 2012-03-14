package com.steambeat.domain.analytics;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class Relation extends BaseEntity {

    // do not delete constructor for mongolink
    public Relation() {
    }

    public Relation(final Subject left, final Subject right) {
        this.id = UUID.randomUUID();
        leftId = left.getId();
        rightId = right.getId();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public Subject getLeft() {
        return Repositories.subjects().get(leftId);
    }

    public Subject getRight() {
        return Repositories.subjects().get(rightId);
    }

    public UUID getLeftId() {
        return leftId;
    }

    public UUID getRightId() {
        return rightId;
    }

    private UUID leftId;
    private UUID rightId;
    private UUID id;
}
