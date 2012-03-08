package com.steambeat.domain.subject;

import com.steambeat.domain.Entity;
import com.steambeat.repositories.Repositories;

public class Relation implements Entity {

    public Relation(final Subject left, final Subject right) {
        this.leftId = left.getId();
        this.rightId = right.getId();
    }

    @Override
    public Object getId() {
        return null;
    }

    public Subject getLeft() {
        return Repositories.subjects().get(leftId);
    }

    public Subject getRight() {
        return Repositories.subjects().get(rightId);
    }

    public String getLeftId() {
        return leftId;
    }

    public String getRightId() {
        return rightId;
    }

    private final String leftId;
    private final String rightId;
}
