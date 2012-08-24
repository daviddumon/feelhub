package com.steambeat.domain.illustration;

import com.google.common.collect.Lists;
import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.steambeat.domain.DomainException;
import com.steambeat.domain.bingsearch.BingLink;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.reference.ConceptReferencesChangedEvent;
import com.steambeat.repositories.*;

import java.util.*;

public class ConceptIllustrationManager {

    @Inject
    public ConceptIllustrationManager(final BingLink bingLink, final SessionProvider sessionProvider) {
        this.bingLink = bingLink;
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final ConceptReferencesChangedEvent eventConcept) {
        sessionProvider.start();
        final List<Illustration> illustrations = getAllIllustrations(eventConcept);
        if (illustrations.isEmpty()) {
            addAnIllustration(eventConcept);
        } else {
            migrateExistingIllustrations(illustrations, eventConcept.getNewReferenceId());
            removeDuplicate(illustrations);
        }
        sessionProvider.stop();
    }

    private List<Illustration> getAllIllustrations(final ConceptReferencesChangedEvent eventConcept) {
        List<Illustration> illustrations = Lists.newArrayList();
        for (UUID referenceId : getReferenceIdList(eventConcept)) {
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

    private List<UUID> getReferenceIdList(final ConceptReferencesChangedEvent eventConcept) {
        final List<UUID> referenceIdList = eventConcept.getReferenceIds();
        referenceIdList.add(eventConcept.getNewReferenceId());
        return referenceIdList;
    }

    private void addAnIllustration(final ConceptReferencesChangedEvent eventConcept) {
        final String link = getLink(eventConcept);
        final Illustration illustration = new Illustration(eventConcept.getNewReferenceId(), link);
        Repositories.illustrations().add(illustration);
    }

    private String getLink(final ConceptReferencesChangedEvent eventConcept) {
        final Keyword keyword = getKeywordFor(eventConcept);
        return bingLink.getIllustration(keyword);
    }

    private Keyword getKeywordFor(final ConceptReferencesChangedEvent eventConcept) {
        final List<Keyword> keywords = Repositories.keywords().forReferenceId(eventConcept.getNewReferenceId());
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
    private SessionProvider sessionProvider;
}
