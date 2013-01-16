package com.feelhub.domain.alchemy;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.translation.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.*;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;
import org.mockito.Matchers;

import java.util.List;

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
    public void ifNoEntitiesCreateNothing() {
        when(entityProvider.entitiesFor(Matchers.any(HttpTopic.class))).thenReturn(TestFactories.namedEntities().namedEntityWithoutTags());
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(httpTopic);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.alchemyEntities().getAll().size()).isZero();
        assertThat(Repositories.topics().getAll().size()).isEqualTo(1);
    }

    @Test
    public void canAnalyseTheGoodUri() {
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(httpTopic);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        verify(entityProvider).entitiesFor(httpTopic);
    }

    @Test
    public void canCreateFromNamedEntity() {
        when(entityProvider.entitiesFor(Matchers.any(HttpTopic.class))).thenReturn(TestFactories.namedEntities().namedEntityWith1Tag());
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(httpTopic);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.topics().getAll().size()).isEqualTo(2);
        assertThat(Repositories.alchemyEntities().getAll().size()).isEqualTo(1);
    }

    @Test
    public void createTagForEachTagInNamedEntity() {
        when(entityProvider.entitiesFor(Matchers.any(HttpTopic.class))).thenReturn(TestFactories.namedEntities().namedEntityWith2Tags());
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(httpTopic);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.tags().getAll().size()).isEqualTo(2);
    }

    @Test
    public void doNotCreateTopicsIfExists() {
        final List<NamedEntity> namedEntities = TestFactories.namedEntities().namedEntityWith1Tag();
        when(entityProvider.entitiesFor(Matchers.any(HttpTopic.class))).thenReturn(namedEntities);
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(httpTopic);
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic(namedEntities.get(0).tags.get(0), namedEntities.get(0).type);
        final Tag tag = TestFactories.tags().newTag(namedEntities.get(0).tags.get(0), realTopic);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.topics().getAll().size()).isEqualTo(2);
    }

    @Test
    public void useUserOfHttpTopicForNewRealTopics() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        when(entityProvider.entitiesFor(Matchers.any(HttpTopic.class))).thenReturn(TestFactories.namedEntities().namedEntityWith1Tag());
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.setUserId(user.getId());
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(httpTopic);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.topics().getAll().get(0).getUserId()).isEqualTo(user.getId());
        assertThat(Repositories.topics().getAll().get(1).getUserId()).isEqualTo(user.getId());
    }

    @Test
    public void doNotMakeAnalysisIfAlreadyExists() {
        final List<NamedEntity> namedEntities = TestFactories.namedEntities().namedEntityWith1Tag();
        when(entityProvider.entitiesFor(Matchers.any(HttpTopic.class))).thenReturn(namedEntities);
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addUri(new Uri("http://www.fake.com"));
        TestFactories.alchemy().newAlchemyAnalysis(httpTopic);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(httpTopic);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.alchemyAnalysis().getAll().size()).isEqualTo(1);
    }

    @Test
    public void doNotStoreAlchemyEntityIfOneAlreadyExists() {
        final List<NamedEntity> namedEntities = TestFactories.namedEntities().namedEntityWith1Tag();
        when(entityProvider.entitiesFor(Matchers.any(HttpTopic.class))).thenReturn(namedEntities);
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic(namedEntities.get(0).tags.get(0), namedEntities.get(0).type);
        TestFactories.tags().newTag(namedEntities.get(0).tags.get(0), realTopic);
        TestFactories.alchemy().newAlchemyEntity(realTopic.getId());
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(httpTopic);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.alchemyEntities().getAll().size()).isEqualTo(1);
    }

    @Test
    public void createRelationsOnSuccess() {
        when(entityProvider.entitiesFor(Matchers.any(HttpTopic.class))).thenReturn(TestFactories.namedEntities().namedEntityWith1Tag());
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(httpTopic);

        DomainEventBus.INSTANCE.post(alchemyRequestEvent);

        assertThat(Repositories.relations().getAll().size()).isEqualTo(2);
    }

    private NamedEntityProvider entityProvider;
}
