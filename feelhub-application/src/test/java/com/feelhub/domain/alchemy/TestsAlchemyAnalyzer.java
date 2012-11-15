package com.feelhub.domain.alchemy;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.translation.*;
import com.feelhub.repositories.*;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;
import org.mockito.Matchers;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class TestsAlchemyAnalyzer {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void setUp() throws Exception {
        entityProvider = mock(NamedEntityProvider.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(SessionProvider.class).to(FakeSessionProvider.class);
                bind(NamedEntityProvider.class).toInstance(entityProvider);
                bind(Translator.class).to(FakeTranslator.class);
            }
        });
        injector.getInstance(AlchemyAnalyzer.class);
    }

    @Test
    public void ifNoKeywordsCreateNothing() {
        when(entityProvider.entitiesFor(Matchers.any(Keyword.class))).thenReturn(TestFactories.namedEntities().namedEntityWithoutKeywords());
        final Keyword keyword = TestFactories.keywords().newWord();
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.alchemyEntities().getAll().size()).isZero();
        assertThat(Repositories.keywords().getAll().size()).isEqualTo(1);
    }

    @Test
    public void canAnalyseTheGoodUri() {
        final Topic topic = TestFactories.topics().newTopic();
        final Keyword keyword = TestFactories.keywords().newWord("http://www.google.fr", FeelhubLanguage.none(), topic);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        verify(entityProvider).entitiesFor(keyword);
    }

    @Test
    public void canCreateFromNamedEntity() {
        when(entityProvider.entitiesFor(Matchers.any(Keyword.class))).thenReturn(TestFactories.namedEntities().namedEntityWith1Keyword());
        final Topic topic = TestFactories.topics().newTopic();
        final Keyword keyword = TestFactories.keywords().newWord("http://www.google.fr", FeelhubLanguage.none(), topic);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.keywords().getAll().size()).isEqualTo(2);
        assertThat(Repositories.topics().getAll().size()).isEqualTo(2);
        assertThat(Repositories.alchemyEntities().getAll().size()).isEqualTo(1);
    }

    @Test
    public void mergeKeywordsForSameAlchemyEntity() {
        when(entityProvider.entitiesFor(Matchers.any(Keyword.class))).thenReturn(TestFactories.namedEntities().namedEntityWith2Keywords());
        final Topic topic = TestFactories.topics().newTopic();
        final Keyword keyword = TestFactories.keywords().newWord("http://www.google.fr", FeelhubLanguage.none(), topic);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.keywords().getAll().size()).isEqualTo(3);
        assertThat(Repositories.topics().getAll().size()).isEqualTo(3);
        assertThat(Repositories.alchemyEntities().getAll().size()).isEqualTo(1);
    }

    @Test
    public void doNotMakeAnalysisIfAlreadyExists() {
        when(entityProvider.entitiesFor(Matchers.any(Keyword.class))).thenReturn(TestFactories.namedEntities().namedEntityWith1Keyword());
        final Topic topic = TestFactories.topics().newTopic();
        final Keyword keyword = TestFactories.keywords().newWord("http://www.google.fr", FeelhubLanguage.none(), topic);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);
        TestFactories.alchemy().newAlchemyAnalysis(topic);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.alchemyAnalysis().getAll().size()).isEqualTo(1);
    }

    @Test
    public void doNotStoreAlchemyEntityIfOneAlreadyExists() {
        when(entityProvider.entitiesFor(Matchers.any(Keyword.class))).thenReturn(TestFactories.namedEntities().namedEntityWith1Keyword());
        final Topic topic = TestFactories.topics().newTopic();
        final Keyword keyword = TestFactories.keywords().newWord("http://www.google.fr", FeelhubLanguage.none(), topic);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);
        final Word word = TestFactories.keywords().newWord("Keyword1", FeelhubLanguage.forString("english"));
        final AlchemyEntity alchemyEntity = new AlchemyEntity(word.getTopicId());
        Repositories.alchemyEntities().add(alchemyEntity);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.alchemyEntities().getAll().size()).isEqualTo(1);
    }

    @Test
    public void createRelationsOnSuccess() {
        when(entityProvider.entitiesFor(Matchers.any(Keyword.class))).thenReturn(TestFactories.namedEntities().namedEntityWith1Keyword());
        final Topic topic = TestFactories.topics().newTopic();
        final Keyword keyword = TestFactories.keywords().newWord("http://www.google.fr", FeelhubLanguage.none(), topic);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(keyword);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.relations().getAll().size()).isEqualTo(2);
    }

    private NamedEntityProvider entityProvider;
}
