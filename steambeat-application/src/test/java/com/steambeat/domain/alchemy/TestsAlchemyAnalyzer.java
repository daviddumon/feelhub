package com.steambeat.domain.alchemy;

import com.steambeat.application.*;
import com.steambeat.domain.concept.ConceptGroupEvent;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class TestsAlchemyAnalyzer {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void setUp() throws Exception {
        entityProvider = mock(NamedEntityProvider.class);
        new AlchemyAnalyzer(new FakeSessionProvider(), entityProvider, new KeywordService(new KeywordFactory(), new ReferenceService(new ReferenceFactory())));
    }

    @Test
    public void ifNoKeywordsCreateNothing() {
        when(entityProvider.entitiesFor(anyString())).thenReturn(TestFactories.namedEntities().namedEntityWithoutKeywords());
        final Reference reference = TestFactories.references().newReference();
        final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());

        DomainEventBus.INSTANCE.post(event);

        assertThat(Repositories.alchemys().getAll().size(), is(0));
        assertThat(Repositories.keywords().getAll().size(), is(0));
        assertThat(Repositories.references().getAll().size(), is(1));
    }

    @Test
    public void canAnalyseTheGoodUri() {
        final Reference reference = TestFactories.references().newReference();
        final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
        final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());

        DomainEventBus.INSTANCE.post(event);

        verify(entityProvider).entitiesFor(keyword.getValue());
    }

    @Test
    public void postConceptGroupEvent() {
        bus.capture(ConceptGroupEvent.class);
        final Reference reference = TestFactories.references().newReference();
        final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
        final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());

        DomainEventBus.INSTANCE.post(event);

        final ConceptGroupEvent conceptGroupEvent = bus.lastEvent(ConceptGroupEvent.class);
        assertThat(conceptGroupEvent, notNullValue());
    }

    @Test
    public void canCreateFromNamedEntity() {
        when(entityProvider.entitiesFor(anyString())).thenReturn(TestFactories.namedEntities().namedEntityWith1Keyword());
        final Reference reference = TestFactories.references().newReference();
        TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
        final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());

        DomainEventBus.INSTANCE.post(event);

        assertThat(Repositories.keywords().getAll().size(), is(2));
        assertThat(Repositories.references().getAll().size(), is(2));
        assertThat(Repositories.alchemys().getAll().size(), is(1));
    }

    @Test
    public void keepTheUriReferenceId() {
        bus.capture(ConceptGroupEvent.class);
        final Reference reference = TestFactories.references().newReference();
        TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
        final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());

        DomainEventBus.INSTANCE.post(event);

        final ConceptGroupEvent conceptGroupEvent = bus.lastEvent(ConceptGroupEvent.class);
        assertThat(conceptGroupEvent, notNullValue());
        assertThat(conceptGroupEvent.getReferenceId(), is(event.getNewReferenceId()));
    }

    private NamedEntityProvider entityProvider;
}
