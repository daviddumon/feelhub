package com.feelhub.domain.bing;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopicType;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
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
        bingSearch = new BingSearch(new FakeBingLink(), relationBinder);
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
        assertThat(image.getIllustration()).isEqualTo("querylink");
        assertThat(image.getUris()).contains(new Uri("querylink"));
    }

    @Test
    public void createRelationsBetweenTopicAndImages() {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), RealTopicType.Automobile);
        Repositories.topics().add(realTopic);

        bingSearch.doBingSearch(realTopic, "query");

        verify(relationBinder).bind(any(Topic.class), anyList());
    }

    private BingSearch bingSearch;
    private BingRelationBinder relationBinder;
}
