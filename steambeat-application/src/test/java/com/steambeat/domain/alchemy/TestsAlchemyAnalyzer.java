package com.steambeat.domain.alchemy;

import com.steambeat.application.*;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.domain.translation.FakeTranslator;
import com.steambeat.domain.uri.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestsAlchemyAnalyzer {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void setUp() throws Exception {
        entityProvider = mock(NamedEntityProvider.class);
        new AlchemyAnalyzer(new FakeSessionProvider(), entityProvider, new KeywordService(new ReferenceService(new ReferenceFactory()), new KeywordFactory(), new FakeTranslator(), new UriManager(new FakeUriResolver())));
    }

    @Test
    public void ifNoKeywordsCreateNothing() {
        when(entityProvider.entitiesFor(anyString())).thenReturn(TestFactories.namedEntities().namedEntityWithoutKeywords());
        final Keyword keyword = TestFactories.keywords().newKeyword();
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.alchemyEntities().getAll().size(), is(0));
        assertThat(Repositories.keywords().getAll().size(), is(1));
    }

    @Test
    public void canAnalyseTheGoodUri() {
        final Reference reference = TestFactories.references().newReference();
        final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        verify(entityProvider).entitiesFor(keyword.getValue());
    }

    @Test
    public void canCreateFromNamedEntity() {
        when(entityProvider.entitiesFor(anyString())).thenReturn(TestFactories.namedEntities().namedEntityWith1Keyword());
        final Reference reference = TestFactories.references().newReference();
        final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.keywords().getAll().size(), is(2));
        assertThat(Repositories.references().getAll().size(), is(2));
        assertThat(Repositories.alchemyEntities().getAll().size(), is(1));
    }

    @Test
    public void mergeKeywordsForSameAlchemyEntity() {
        when(entityProvider.entitiesFor(anyString())).thenReturn(TestFactories.namedEntities().namedEntityWith2Keywords());
        final Reference reference = TestFactories.references().newReference();
        final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.keywords().getAll().size(), is(3));
        assertThat(Repositories.references().getAll().size(), is(3));
        assertThat(Repositories.alchemyEntities().getAll().size(), is(1));
    }

    @Test
    public void createAlchemyAnalysisOnSuccess() {
        when(entityProvider.entitiesFor(anyString())).thenReturn(TestFactories.namedEntities().namedEntityWith1Keyword());
        final Reference reference = TestFactories.references().newReference();
        final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.alchemyAnalysis().getAll().size(), is(1));
    }

    @Test
    public void doNotMakeAnalysisIfAlreadyExists() {
        when(entityProvider.entitiesFor(anyString())).thenReturn(TestFactories.namedEntities().namedEntityWith1Keyword());
        final Reference reference = TestFactories.references().newReference();
        final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);
        TestFactories.alchemy().newAlchemyAnalysis(reference);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.alchemyAnalysis().getAll().size(), is(1));
    }

    private NamedEntityProvider entityProvider;
}
