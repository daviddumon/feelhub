package com.feelhub.domain.cloudinary;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.media.MediaCreatedEvent;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Maps;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.IOException;
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
    }

    @Test
    public void tryToGetThumbnail() throws IOException {
        final String source = "source";
        final Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_564,h_348,c_fill,g_face,q_80");
        params.put("file", source);

        cloudinary.getThumbnail(source);

        verify(cloudinaryLink).getIllustration(params);
    }

    @Test
    public void throwCloudinaryExceptionOnAnyError() throws IOException {
        exception.expect(CloudinaryException.class);
        when(cloudinaryLink.getIllustration(anyMap())).thenThrow(new IOException());

        cloudinary.getThumbnail("source");
    }

    @Test
    public void topicHasAnIllustration() throws IOException {
        when(cloudinaryLink.getIllustration(anyMap())).thenReturn("thumbnail");
        final RealTopic realTopic = TestFactories.topics().newSimpleRealTopic(RealTopicType.Automobile);
        final HttpTopic image = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Image);
        final MediaCreatedEvent mediaCreatedEvent = new MediaCreatedEvent(realTopic.getCurrentId(), image.getCurrentId());

        DomainEventBus.INSTANCE.post(mediaCreatedEvent);

        assertThat(realTopic.getThumbnail()).isEqualTo("thumbnail");
    }

    private Cloudinary cloudinary;
    private CloudinaryLink cloudinaryLink;
}
