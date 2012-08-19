package com.steambeat.domain.illustration;

import com.google.common.collect.Lists;
import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.steambeat.domain.DomainException;
import com.steambeat.domain.bingsearch.BingLink;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.reference.ReferencesChangedEvent;
import com.steambeat.repositories.Repositories;

import java.util.*;

public class IllustrationManager {

    @Inject
    public IllustrationManager(final BingLink bingLink) {
        this.bingLink = bingLink;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final ReferencesChangedEvent event) {
        final List<Illustration> illustrations = getAllIllustrations(event);
        if (illustrations.isEmpty()) {
            addAnIllustration(event);
        } else {
            migrateExistingIllustrations(illustrations, event.getNewReferenceId());
            removeDuplicate(illustrations);
        }
    }

    private List<Illustration> getAllIllustrations(final ReferencesChangedEvent event) {
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

    private void addAnIllustration(final ReferencesChangedEvent event) {
        final String link = getLink(event);
        final Illustration illustration = new Illustration(event.getNewReferenceId(), link);
        Repositories.illustrations().add(illustration);
    }

    private String getLink(final ReferencesChangedEvent event) {
        final Keyword keyword = getKeywordFor(event);
        return bingLink.getIllustration(keyword);
    }

    private Keyword getKeywordFor(final ReferencesChangedEvent event) {
        final List<Keyword> keywords = Repositories.keywords().forReferenceId(event.getNewReferenceId());
        if (keywords != null) {
            return keywords.get(0);
        } else {
            throw new DomainException("the fuck just happens ????");
        }
    }

    private void migrateExistingIllustrations(final List<Illustration> illustrations, final UUID newReference) {
        for (Illustration illustration : illustrations) {
            if (illustration.getReferenceId() != newReference) {
                illustration.setReferenceId(newReference);
            }
        }
    }

    private void removeDuplicate(final List<Illustration> illustrations) {
        if (illustrations.size() > 1) {
            for (int i = 1; i < illustrations.size(); i++) {
                Repositories.illustrations().delete(illustrations.get(i));
            }
        }
    }

    private BingLink bingLink;
}
