package com.steambeat.domain.illustration;

import com.google.common.collect.Lists;
import com.steambeat.domain.DomainException;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.reference.*;
import com.steambeat.repositories.Repositories;

import java.util.*;

public abstract class IllustrationManager {

    protected List<Illustration> getAllIllustrations(final ReferencesChangedEvent event) {
        List<Illustration> illustrations = Lists.newArrayList();
        for (UUID referenceId : getReferenceIdList(event)) {
            final Illustration illustration = getIllustrationFor(referenceId);
            if (illustration != null) {
                illustrations.add(illustration);
            }
        }
        return illustrations;
    }

    private Illustration getIllustrationFor(final UUID referenceId) {
        final List<Illustration> illustrations = Repositories.illustrations().forReferenceId(referenceId);
        if (!illustrations.isEmpty()) {
            return illustrations.get(0);
        } else {
            return null;
        }
    }

    private List<UUID> getReferenceIdList(final ReferencesChangedEvent event) {
        final List<UUID> referenceIdList = event.getReferenceIds();
        referenceIdList.add(event.getNewReferenceId());
        return referenceIdList;
    }

    protected void migrateExistingIllustrations(final List<Illustration> illustrations, final UUID newReference) {
        for (Illustration illustration : illustrations) {
            if (illustration.getReferenceId() != newReference) {
                illustration.setReferenceId(newReference);
            }
        }
    }

    protected void removeDuplicate(final List<Illustration> illustrations) {
        if (illustrations.size() > 1) {
            for (int i = 1; i < illustrations.size(); i++) {
                Repositories.illustrations().delete(illustrations.get(i));
            }
        }
    }

    protected Keyword getKeywordFor(final ReferencesChangedEvent event) {
        final List<Keyword> keywords = Repositories.keywords().forReferenceId(event.getNewReferenceId());
        if (keywords != null) {
            return keywords.get(0);
        } else {
            throw new DomainException("the fuck just happens ????");
        }
    }
}
