package com.feelhub.domain.relation;

import java.util.UUID;

public class Related extends Relation {

    //mongoling
    protected Related() {

    }

    public Related(final UUID fromId, final UUID toId, final double weight) {
        super(fromId, toId, weight);
    }
}
