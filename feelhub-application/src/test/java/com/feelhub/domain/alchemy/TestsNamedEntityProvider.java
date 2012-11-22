package com.feelhub.domain.alchemy;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsNamedEntityProvider {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(AlchemyLink.class).to(FakeJsonAlchemyLink.class);
            }
        });
        namedEntityProvider = injector.getInstance(NamedEntityProvider.class);
    }

    @Test
    public void canGetNamedEntitiesForAnUri() {
        final Topic topic = TestFactories.topics().newTopic();
        final String uri = "http://www.mypage.com";

        final List<NamedEntity> results = namedEntityProvider.entitiesFor(topic, uri);

        assertThat(results).isNotNull();
        assertThat(results.size()).isEqualTo(19);
    }

    @Test
    public void createAlchemyAnalysisForUri() {
        final Topic topic = TestFactories.topics().newTopic();
        final String uri = "http://www.mypage.com";

        namedEntityProvider.entitiesFor(topic, uri);

        final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().getAll();
        assertThat(alchemyAnalysisList.size()).isEqualTo(1);
        assertThat(alchemyAnalysisList.get(0).getTopicId()).isEqualTo(topic.getId());
        assertThat(alchemyAnalysisList.get(0).getLanguageCode()).isEqualTo(FeelhubLanguage.fromCountryName("english").getCode());
    }

    @Test
    public void throwExceptionOnError() {
        exception.expect(AlchemyException.class);
        final Topic topic = TestFactories.topics().newTopic();
        final String uri = "http://www.error.com";

        namedEntityProvider.entitiesFor(topic, uri);
    }

    private NamedEntityProvider namedEntityProvider;
}
