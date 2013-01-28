package com.feelhub.domain.relation.related;

import com.feelhub.domain.relation.Relation;

import java.util.UUID;

public class Related extends Relation {

    //mongoling
    protected Related() {

    }

    public Related(final UUID fromId, final UUID toId, final double weight) {
        super(fromId, toId, weight);
    }
}
