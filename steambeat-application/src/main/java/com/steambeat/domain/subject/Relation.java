package com.steambeat.domain.subject;

import com.steambeat.domain.Entity;
import com.steambeat.repositories.Repositories;

public class Relation implements Entity {

    // do not delete constructor for mongolink
    public Relation() {
    }

    public Relation(final Subject left, final Subject right) {
        leftId = left.getId();
        rightId = right.getId();
    }

    @Override
    public Object getId() {
        return id;
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

    private String leftId;
    private String rightId;
    private String id;
}
