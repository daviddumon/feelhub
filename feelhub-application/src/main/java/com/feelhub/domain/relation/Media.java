package com.feelhub.domain.relation;

import java.util.UUID;

public class Media extends Relation {

    //mongolink
    protected Media() {

    }

    public Media(final UUID fromId, final UUID toId) {
        super(fromId, toId, 0.0);
    }
}
