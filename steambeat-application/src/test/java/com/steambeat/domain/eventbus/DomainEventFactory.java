package com.steambeat.domain.eventbus;

import com.steambeat.domain.concept.*;

public class DomainEventFactory {

    public ConceptEvent newConceptEvent() {
        final ConceptEvent conceptEvent = new ConceptEvent();
        return conceptEvent;
    }

    public ConceptTranslatedEvent newConceptTranslatedEvent() {
        final ConceptTranslatedEvent conceptTranslatedEvent = new ConceptTranslatedEvent();
        return conceptTranslatedEvent;
    }
}
