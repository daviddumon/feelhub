package com.feelhub.domain.opinion;

import java.util.UUID;

public class Judgment {

    // Constructor for mapper : do not delete !
    protected Judgment() {
    }

    public Judgment(final UUID referenceId, final Feeling feeling) {
        this.referenceId = referenceId;
        this.feeling = feeling;
    }

    public Feeling getFeeling() {
        return feeling;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(final UUID referenceId) {
        this.referenceId = referenceId;
    }

    private UUID referenceId;
    private Feeling feeling;
}
