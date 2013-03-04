package com.feelhub.domain.related;

import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class RelatedBuilderTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        relatedBuilder = injector.getInstance(RelatedBuilder.class);
    }

    @Test
    public void canConnectTwoTopicsWithNoPreviousConnection() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();

        relatedBuilder.connectTwoWays(from, to);

        final List<Related> relatedList = Repositories.related().getAll();
        assertThat(relatedList.size()).isEqualTo(2);
        assertThat(relatedList.get(0).getWeight()).isEqualTo(1.0);
        assertThat(relatedList.get(1).getWeight()).isEqualTo(1.0);
    }

    @Test
    public void canConnectTwoTopicsWithAnExistingOneWayRelation() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelated(from.getId(), to.getId());

        relatedBuilder.connectTwoWays(from, to);

        final List<Related> relatedList = Repositories.related().getAll();
        assertThat(relatedList.size()).isEqualTo(2);
        assertThat(relatedList.get(0).getWeight()).isEqualTo(2.0);
        assertThat(relatedList.get(1).getWeight()).isEqualTo(1.0);
    }

    @Test
    public void doNotConnectTwoIdenticalTopics() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        relatedBuilder.connectTwoWays(realTopic, realTopic);

        final List<Related> relatedList = Repositories.related().getAll();
        assertThat(relatedList.size()).isZero();
    }

    private RelatedBuilder relatedBuilder;
}
