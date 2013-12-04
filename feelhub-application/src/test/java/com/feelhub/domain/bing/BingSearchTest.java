package com.feelhub.domain.bing;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.ThumbnailCreatedEvent;
import com.feelhub.domain.topic.http.uri.FakeUriResolver;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import org.junit.*;
import org.restlet.data.MediaType;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class BingSearchTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        final FakeUriResolver fakeUriResolver = new FakeUriResolver();
        fakeUriResolver.thatFind(MediaType.IMAGE_JPEG);
        bingSearch = new BingSearch(new FakeBingLink(), fakeUriResolver);
        bingSearch.uriResolver = fakeUriResolver;
    }

    @Test
    public void postThumbnailCreatedEvent() {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), RealTopicType.Automobile);
        Repositories.topics().add(realTopic);

        bingSearch.doBingSearch(realTopic, "query");

        assertThat(Repositories.topics().getAll().size()).isEqualTo(1);
        final ThumbnailCreatedEvent thumbnailCreatedEvent = bus.lastEvent(ThumbnailCreatedEvent.class);
        assertThat(thumbnailCreatedEvent).isNotNull();
        assertThat(thumbnailCreatedEvent.getTopicId()).isEqualTo(realTopic.getCurrentId());
        assertThat(thumbnailCreatedEvent.getThumbnails()).hasSize(1);
        assertThat(thumbnailCreatedEvent.getThumbnails().get(0).getOrigin()).isEqualTo("http://querylink");
    }

    @Test
    public void postThumbnailCreatedEventOnThumbnailUpdateRequested() {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), RealTopicType.Automobile);
        Repositories.topics().add(realTopic);

        bingSearch.onRealTopicThumbnailUpdateRequested(new RealTopicThumbnailUpdateRequestedEvent(realTopic.getId(), FeelhubLanguage.fromCode(realTopic.getLanguageCode())));

        final ThumbnailCreatedEvent thumbnailCreatedEvent = bus.lastEvent(ThumbnailCreatedEvent.class);
        assertThat(thumbnailCreatedEvent).isNotNull();
        assertThat(thumbnailCreatedEvent.getTopicId()).isEqualTo(realTopic.getCurrentId());
    }

    private BingSearch bingSearch;
}
