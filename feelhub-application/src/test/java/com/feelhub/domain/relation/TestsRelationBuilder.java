package com.feelhub.domain.relation;

import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsRelationBuilder {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        relationBuilder = injector.getInstance(RelationBuilder.class);
    }

    @Test
    public void canConnectTwoTopicsWithNoPreviousConnection() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();

        relationBuilder.connectTwoWays(from, to);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size()).isEqualTo(2);
        assertThat(relations.get(0).getWeight()).isEqualTo(1.0);
        assertThat(relations.get(1).getWeight()).isEqualTo(1.0);
    }

    @Test
    public void canConnectTwoTopicsWithAnExistingOneWayRelation() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelated(from.getId(), to.getId());

        relationBuilder.connectTwoWays(from, to);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size()).isEqualTo(2);
        assertThat(relations.get(0).getWeight()).isEqualTo(2.0);
        assertThat(relations.get(1).getWeight()).isEqualTo(1.0);
    }

    @Test
    public void doNotConnectTwoIdenticalTopics() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        relationBuilder.connectTwoWays(realTopic, realTopic);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size()).isZero();
    }

    @Test
    public void canCreateRelatedRelations() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();

        relationBuilder.connectTwoWays(from, to);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.get(0).getClass()).isEqualTo(Related.class);
        assertThat(relations.get(1).getClass()).isEqualTo(Related.class);
    }

    @Test
    public void canCreateMediaRelations() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final HttpTopic to = TestFactories.topics().newMediaTopic();

        relationBuilder.connectTwoWays(from, to);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.get(0).getClass()).isEqualTo(Media.class);
        assertThat(relations.get(1).getClass()).isEqualTo(Related.class);
    }

    private RelationBuilder relationBuilder;
}
