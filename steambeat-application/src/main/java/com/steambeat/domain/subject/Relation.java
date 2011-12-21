package com.steambeat.domain.subject;

import com.steambeat.domain.Entity;
import com.steambeat.repositories.Repositories;

public class Relation implements Entity {

    public Relation(Subject left, Subject right) {
        this.leftId = left.getId();
        this.rightId = right.getId();
        this.right = right;
    }

    @Override
    public Object getId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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

    private Subject right;
    private String leftId;
    private String rightId;
}
