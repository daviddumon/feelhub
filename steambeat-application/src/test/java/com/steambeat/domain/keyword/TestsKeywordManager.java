package com.steambeat.domain.keyword;

import com.steambeat.domain.concept.*;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.TestFactories;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestsKeywordManager {


    //@Test
    //public void newReferenceIsOldest() {
    //    final Concept concept = TestFactories.concepts().newConcept();
    //    final Keyword oldestKeyword = TestFactories.keywords().newKeyword("fr", SteambeatLanguage.forString("fr"));
    //    concept.addIfAbsent(oldestKeyword);
    //    time.waitDays(1);
    //    concept.addIfAbsent(TestFactories.keywords().newKeyword("en", SteambeatLanguage.forString("en")));
    //    concept.addIfAbsent(TestFactories.keywords().newKeyword("de", SteambeatLanguage.forString("de")));
    //    final ConceptTranslatedEvent event = new ConceptTranslatedEvent(concept);
    //
    //    DomainEventBus.INSTANCE.post(event);
    //
    //    final List<Keyword> keywords = Repositories.keywords().getAll();
    //    assertThat(keywords.get(0).getReferenceId(), is(oldestKeyword.getReferenceId()));
    //    assertThat(keywords.get(1).getReferenceId(), is(oldestKeyword.getReferenceId()));
    //    assertThat(keywords.get(2).getReferenceId(), is(oldestKeyword.getReferenceId()));
    //}
}
