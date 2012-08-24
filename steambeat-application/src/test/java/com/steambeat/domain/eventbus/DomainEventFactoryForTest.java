package com.steambeat.domain.eventbus;

import com.steambeat.domain.concept.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.reference.ConceptReferencesChangedEvent;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.domain.uri.*;
import com.steambeat.test.TestFactories;

import java.util.UUID;

public class DomainEventFactoryForTest {

    public ConceptEvent newConceptEvent() {
        final ConceptEvent conceptEvent = new ConceptEvent();
        return conceptEvent;
    }

    public ConceptTranslatedEvent newConceptTranslatedEvent() {
        final ConceptTranslatedEvent conceptTranslatedEvent = new ConceptTranslatedEvent();
        return conceptTranslatedEvent;
    }

    public CompleteUriEvent newCompleteUriEvent() {
        final CompleteUriEvent completeUriEvent = new CompleteUriEvent();
        return completeUriEvent;
    }

    public UriEvent newUriEvent(final String address) {
        final Keyword keyword = TestFactories.keywords().newKeyword(address, SteambeatLanguage.none());
        final UriEvent uriEvent = new UriEvent(keyword);
        return uriEvent;
    }

    public ConceptReferencesChangedEvent newConceptReferencesChangedEvent(final UUID newReference) {
        final ConceptReferencesChangedEvent conceptReferencesChangedEvent = new ConceptReferencesChangedEvent(newReference);
        return conceptReferencesChangedEvent;
    }
}
