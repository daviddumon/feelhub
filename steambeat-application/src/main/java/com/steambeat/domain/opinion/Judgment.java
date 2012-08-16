package com.steambeat.domain.opinion;

import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class Judgment {

    // Constructor for mapper : do not delete !
    protected Judgment() {
    }

    public Judgment(final Reference reference, final Feeling feeling) {
        this.referenceId = reference.getId();
        this.feeling = feeling;
    }

    public Feeling getFeeling() {
        return feeling;
    }

    public Reference getReference() {
        return Repositories.references().get(referenceId);
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    private UUID referenceId;
    private Feeling feeling;
}
