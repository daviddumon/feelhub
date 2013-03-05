package com.feelhub.domain.feeling;

import java.util.UUID;

public class FeelingFactory {

    public Feeling createFeeling(final String text, final UUID userId) {
        final Feeling feeling = new Feeling(UUID.randomUUID(), text, userId);
        return feeling;
    }
}
