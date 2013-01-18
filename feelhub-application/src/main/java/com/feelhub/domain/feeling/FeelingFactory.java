package com.feelhub.domain.feeling;

import java.util.UUID;

public class FeelingFactory {

    public Feeling createFeeling(final UUID id, final String text, final UUID userId) {
        final Feeling feeling = new Feeling(id, text, userId);
        return feeling;
    }
}
