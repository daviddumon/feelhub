package com.steambeat.domain.keyword;

import com.steambeat.repositories.Repositories;

import java.util.*;

public class KeywordManager {

    public void migrate(final UUID newReferenceId, final List<UUID> oldReferenceIds) {
        for (final UUID oldReferenceId : oldReferenceIds) {
            final List<Keyword> keywords = Repositories.keywords().forReferenceId(oldReferenceId);
            for (final Keyword keyword : keywords) {
                keyword.setReferenceId(newReferenceId);
            }
        }
    }
}
