package com.feelhub.domain.alchemy;

import com.feelhub.application.*;
import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.keyword.*;
import com.feelhub.domain.relation.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.translation.FakeTranslator;
import com.feelhub.domain.uri.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import org.junit.*;
import org.mockito.Matchers;

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
        new AlchemyAnalyzer(new FakeSessionProvider(), entityProvider, new KeywordService(new TopicService(new TopicFactory()), new KeywordFactory(), new FakeTranslator(), new UriManager(new FakeUriResolver())), new AlchemyRelationBinder(new RelationBuilder(new RelationFactory())));
    }

    @Test
    public void ifNoKeywordsCreateNothing() {
        when(entityProvider.entitiesFor(Matchers.any(Keyword.class))).thenReturn(TestFactories.namedEntities().namedEntityWithoutKeywords());
        final Keyword keyword = TestFactories.keywords().newKeyword();
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.alchemyEntities().getAll().size(), is(0));
        assertThat(Repositories.keywords().getAll().size(), is(1));
    }

    @Test
    public void canAnalyseTheGoodUri() {
        final Topic topic = TestFactories.topics().newTopic();
        final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", FeelhubLanguage.none(), topic);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        verify(entityProvider).entitiesFor(keyword);
    }

    @Test
    public void canCreateFromNamedEntity() {
        when(entityProvider.entitiesFor(Matchers.any(Keyword.class))).thenReturn(TestFactories.namedEntities().namedEntityWith1Keyword());
        final Topic topic = TestFactories.topics().newTopic();
        final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", FeelhubLanguage.none(), topic);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.keywords().getAll().size(), is(2));
        assertThat(Repositories.topics().getAll().size(), is(2));
        assertThat(Repositories.alchemyEntities().getAll().size(), is(1));
    }

    @Test
    public void mergeKeywordsForSameAlchemyEntity() {
        when(entityProvider.entitiesFor(Matchers.any(Keyword.class))).thenReturn(TestFactories.namedEntities().namedEntityWith2Keywords());
        final Topic topic = TestFactories.topics().newTopic();
        final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", FeelhubLanguage.none(), topic);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.keywords().getAll().size(), is(3));
        assertThat(Repositories.topics().getAll().size(), is(3));
        assertThat(Repositories.alchemyEntities().getAll().size(), is(1));
    }

    @Test
    public void doNotMakeAnalysisIfAlreadyExists() {
        when(entityProvider.entitiesFor(Matchers.any(Keyword.class))).thenReturn(TestFactories.namedEntities().namedEntityWith1Keyword());
        final Topic topic = TestFactories.topics().newTopic();
        final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", FeelhubLanguage.none(), topic);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);
        TestFactories.alchemy().newAlchemyAnalysis(topic);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.alchemyAnalysis().getAll().size(), is(1));
    }

    @Test
    public void createRelationsOnSuccess() {
        when(entityProvider.entitiesFor(Matchers.any(Keyword.class))).thenReturn(TestFactories.namedEntities().namedEntityWith1Keyword());
        final Topic topic = TestFactories.topics().newTopic();
        final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", FeelhubLanguage.none(), topic);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.relations().getAll().size(), is(2));
    }

    private NamedEntityProvider entityProvider;
}
