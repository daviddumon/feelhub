package com.feelhub.domain.bing;

import com.feelhub.domain.media.Media;
import com.feelhub.domain.related.Related;
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

public class BingRelationBinderTest {

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
    public void canBindMediasToTopic() {
        final RealTopic car = TestFactories.topics().newSimpleRealTopic(RealTopicType.Automobile);
        final List<HttpTopic> images = Lists.newArrayList();
        final HttpTopic image1 = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Image);
        final HttpTopic image2 = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Image);
        images.add(image1);
        images.add(image2);

        bingRelationBinder.bind(car, images);

        final List<Related> relatedList = Repositories.related().getAll();
        assertThat(relatedList.size()).isEqualTo(2);
        final List<Media> mediaList = Repositories.medias().getAll();
        assertThat(mediaList.size()).isEqualTo(4);
    }

    private BingRelationBinder bingRelationBinder;
}

