package com.feelhub.domain.bing;

import com.feelhub.domain.cloudinary.*;
import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopicType;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;
import org.restlet.data.MediaType;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class BingSearchTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        relationBinder = mock(BingRelationBinder.class);
        final FakeUriResolver fakeUriResolver = new FakeUriResolver();
        fakeUriResolver.thatFind(MediaType.IMAGE_JPEG);
        bingSearch = new BingSearch(new FakeBingLink(), relationBinder, new Cloudinary(new FakeCloudinaryLink()));
        bingSearch.uriResolver = fakeUriResolver;
    }

    @Test
    public void canCreateImageTopicFromIllustration() {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), RealTopicType.Automobile);
        Repositories.topics().add(realTopic);

        bingSearch.doBingSearch(realTopic, "query");

        assertThat(Repositories.topics().getAll().size()).isEqualTo(2);
        final Topic image = Repositories.topics().getAll().get(1);
        assertThat(image.getType()).isEqualTo(HttpTopicType.Image);
        assertThat(image.getIllustration()).isEqualTo("query Automobilelink");
        assertThat(image.getThumbnailLarge()).isEqualTo("thumbnail");
        assertThat(image.getThumbnailMedium()).isEqualTo("thumbnail");
        assertThat(image.getThumbnailSmall()).isEqualTo("thumbnail");
        assertThat(image.getUris()).contains(new Uri("query Automobilelink"));
    }

    @Test
    public void createRelationsBetweenTopicAndImages() {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), RealTopicType.Automobile);
        Repositories.topics().add(realTopic);

        bingSearch.doBingSearch(realTopic, "query");

        verify(relationBinder).bind(any(Topic.class), anyList());
    }

    @Test
    public void topicHasAnIllustration() {
        final RealTopic realTopic = TestFactories.topics().newSimpleRealTopic(RealTopicType.Automobile);

        bingSearch.doBingSearch(realTopic, "query");

        assertThat(realTopic.getIllustration()).isEqualTo("query Automobilelink");
        assertThat(realTopic.getThumbnailLarge()).isEqualTo("thumbnail");
        assertThat(realTopic.getThumbnailMedium()).isEqualTo("thumbnail");
        assertThat(realTopic.getThumbnailSmall()).isEqualTo("thumbnail");
    }

    private BingSearch bingSearch;
    private BingRelationBinder relationBinder;
}
