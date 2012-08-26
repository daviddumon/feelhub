package com.steambeat.domain.eventbus;

import com.steambeat.domain.concept.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.reference.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.domain.uri.*;
import com.steambeat.test.TestFactories;

import java.util.UUID;

public class DomainEventFactoryForTest {

    public ConceptEvent newConceptEvent() {
        return new ConceptEvent();
    }

    public ConceptTranslatedEvent newConceptTranslatedEvent() {
        return new ConceptTranslatedEvent();
    }

    public CompleteUriEvent newCompleteUriEvent() {
        return new CompleteUriEvent();
    }

    public UriEvent newUriEvent(final String address) {
        final Keyword keyword = TestFactories.keywords().newKeyword(address, SteambeatLanguage.none());
        final UriEvent uriEvent = new UriEvent(keyword);
        return uriEvent;
    }

    public ConceptReferencesChangedEvent newConceptReferencesChangedEvent(final UUID newReference) {
        return new ConceptReferencesChangedEvent(newReference);
    }

    public UriReferencesChangedEvent newUriReferencesChangedEvent(final UUID newReference) {
        return new UriReferencesChangedEvent(newReference);
    }

    public ConceptGroupTranslatedEvent newConceptGroupTranslatedEvent() {
        final Reference reference = TestFactories.references().newReference();
        final ConceptGroupTranslatedEvent conceptGroupTranslatedEvent = new ConceptGroupTranslatedEvent(reference.getId());
        return conceptGroupTranslatedEvent;
    }

    public ConceptGroupReferencesChangedEvent newConceptGroupeReferencesChangedEvent() {
        final Reference reference = TestFactories.references().newReference();
        final ConceptGroupReferencesChangedEvent conceptGroupReferencesChangedEvent = new ConceptGroupReferencesChangedEvent(reference.getId());
        for (int i = 0; i < 5; i++) {
            final ConceptReferencesChangedEvent conceptReferencesChangedEvent = new ConceptReferencesChangedEvent(TestFactories.references().newReference().getId());
            conceptGroupReferencesChangedEvent.addIfAbsent(conceptReferencesChangedEvent);
        }
        return conceptGroupReferencesChangedEvent;
    }
}
