package com.feelhub.domain.cloudinary;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class CloudinaryTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();
    private RealTopic topic;

    @Before
    public void before() {
        cloudinaryLink = mock(CloudinaryLink.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(CloudinaryLink.class).toInstance(cloudinaryLink);
            }
        });
        cloudinary = injector.getInstance(Cloudinary.class);
        topic = TestFactories.topics().newSimpleRealTopic(RealTopicType.Automobile);
    }

    @Test
    public void tryToGetThumbnail() throws IOException {
        String origin = "source";
        final ThumbnailCreatedEvent thumbnailCreatedEvent = getThumbnailCreatedEvent(origin);

        cloudinary.onThumbnailCreatedEvent(thumbnailCreatedEvent);

        verify(cloudinaryLink).getIllustration(transformationParameters(origin));
    }

    private Map<String, String> transformationParameters(String source) {
        final Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_564,h_348,c_fill,g_face,q_75");
        params.put("file", source);
        return params;
    }

    @Test
    public void tryToGetMultipleThumbnails() throws IOException {
        when(cloudinaryLink.getIllustration(transformationParameters("source"))).thenThrow(new IOException());
        final ThumbnailCreatedEvent thumbnailCreatedEvent = getThumbnailCreatedEvent("source", "otherSource");

        cloudinary.onThumbnailCreatedEvent(thumbnailCreatedEvent);

        verify(cloudinaryLink).getIllustration(transformationParameters("otherSource"));
    }

    @Test
    public void throwCloudinaryExceptionOnError() throws IOException {
        final ThumbnailCreatedEvent thumbnailCreatedEvent = getThumbnailCreatedEvent("source");
        when(cloudinaryLink.getIllustration(anyMap())).thenThrow(new IOException());

        exception.expect(CloudinaryException.class);
        cloudinary.onThumbnailCreatedEvent(thumbnailCreatedEvent);
    }

    @Test
    public void topicHasAThumbnail() throws IOException {
        when(cloudinaryLink.getIllustration(anyMap())).thenReturn("thumbnail");
        final ThumbnailCreatedEvent thumbnailCreatedEvent = getThumbnailCreatedEvent("origin");

        DomainEventBus.INSTANCE.post(thumbnailCreatedEvent);

        assertThat(topic.getThumbnail()).isEqualTo("thumbnail");
        assertThat(topic.getThumbnails().size()).isEqualTo(1);
        assertThat(topic.getThumbnails().get(0).getOrigin()).isEqualTo("origin");
        assertThat(topic.getThumbnails().get(0).getCloudinary()).isEqualTo("thumbnail");
    }

    private ThumbnailCreatedEvent getThumbnailCreatedEvent(String... origin) {
        final ThumbnailCreatedEvent thumbnailCreatedEvent = new ThumbnailCreatedEvent();
        thumbnailCreatedEvent.addThumbnails(getThumbnails(origin));
        thumbnailCreatedEvent.setTopicId(topic.getCurrentId());
        return thumbnailCreatedEvent;
    }

    private Thumbnail[] getThumbnails(String[] origins) {
        List<Thumbnail> result = Lists.newArrayList();
        for (String origin : origins) {
            result.add(new Thumbnail(origin));
        }
        return result.toArray(new Thumbnail[result.size()]);
    }

    private Cloudinary cloudinary;
    private CloudinaryLink cloudinaryLink;
}
