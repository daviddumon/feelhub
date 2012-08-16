package com.steambeat.domain.subject.concept;

import java.util.UUID;

public class Concept {

    // mongolink constructor : do not delete
    public Concept() {
    }

    public Concept(final UUID id) {
        this.id = id;
    }

    private UUID id;
}
