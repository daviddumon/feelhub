package com.feelhub.domain.relation;

import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsBingRelationBinder {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        bingRelationBinder = injector.getInstance(BingRelationBinder.class);
    }

    @Test
    public void canBindImagesToTopic() {
        final RealTopic laguna = TestFactories.topics().newSimpleRealTopic(RealTopicType.Automobile);
        final List<HttpTopic> images = Lists.newArrayList();
        final HttpTopic image1 = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Image);
        final HttpTopic image2 = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Image);
        images.add(image1);
        images.add(image2);

        bingRelationBinder.bind(laguna, images);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size()).isEqualTo(6);
    }

    private BingRelationBinder bingRelationBinder;
}

