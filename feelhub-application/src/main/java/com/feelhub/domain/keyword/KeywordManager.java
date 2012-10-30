package com.feelhub.domain.keyword;

import com.feelhub.domain.reference.ReferencePatch;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class KeywordManager {

    public void merge(final ReferencePatch referencePatch) {
        for (final UUID oldReferenceId : referencePatch.getOldReferenceIds()) {
            final List<Keyword> keywords = Repositories.keywords().forReferenceId(oldReferenceId);
            for (final Keyword keyword : keywords) {
                keyword.setReferenceId(referencePatch.getNewReferenceId());
            }
        }
    }
}
