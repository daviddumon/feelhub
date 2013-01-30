package com.feelhub.domain.topic.http;

import com.feelhub.domain.alchemy.AlchemyRequestEvent;
import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import org.junit.*;
import org.restlet.data.MediaType;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsHttpTopic {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void canCreateAHttpTopic() {
        final UUID id = UUID.randomUUID();

        final HttpTopic topic = new HttpTopic(id);

        assertThat(topic.getId()).isEqualTo(id);
        assertThat(topic.getCurrentId()).isEqualTo(id);
    }

    @Test
    public void canSetType() {
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());
        final HttpTopicType type = HttpTopicType.Article;

        topic.setType(type);

        assertThat(topic.getType()).isEqualTo(type);
    }

    @Test
    public void canSetMediaType() {
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());

        topic.setMediaType(MediaType.TEXT_HTML);

        assertThat(topic.getMediaType()).isEqualTo(MediaType.TEXT_HTML);
    }

    @Test
    public void mediaTypeApplicationAutoSetTypeData() {
        final MediaType mediaType = MediaType.APPLICATION_JSON;
        final HttpTopicType type = HttpTopicType.Data;
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());

        topic.setMediaType(mediaType);

        assertThat(topic.getMediaType()).isEqualTo(mediaType);
        assertThat(topic.getType()).isEqualTo(type);
    }

    @Test
    public void mediaTypeAudioAutoSetTypeAudio() {
        final MediaType mediaType = MediaType.AUDIO_BASIC;
        final HttpTopicType type = HttpTopicType.Audio;
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());

        topic.setMediaType(mediaType);

        assertThat(topic.getMediaType()).isEqualTo(mediaType);
        assertThat(topic.getType()).isEqualTo(type);
    }

    @Test
    public void mediaTypeImageAutoSetTypeImage() {
        final MediaType mediaType = MediaType.IMAGE_BMP;
        final HttpTopicType type = HttpTopicType.Image;
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());

        topic.setMediaType(mediaType);

        assertThat(topic.getMediaType()).isEqualTo(mediaType);
        assertThat(topic.getType()).isEqualTo(type);
    }

    @Test
    public void mediaTypeMessageAutoSetTypeOther() {
        final MediaType mediaType = MediaType.MESSAGE_ALL;
        final HttpTopicType type = HttpTopicType.Other;
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());

        topic.setMediaType(mediaType);

        assertThat(topic.getMediaType()).isEqualTo(mediaType);
        assertThat(topic.getType()).isEqualTo(type);
    }

    @Test
    public void mediaTypeModelAutoSetTypeData() {
        final MediaType mediaType = MediaType.MODEL_VRML;
        final HttpTopicType type = HttpTopicType.Data;
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());

        topic.setMediaType(mediaType);

        assertThat(topic.getMediaType()).isEqualTo(mediaType);
        assertThat(topic.getType()).isEqualTo(type);
    }

    @Test
    public void mediaTypeMultipartAutoSetTypeData() {
        final MediaType mediaType = MediaType.MULTIPART_FORM_DATA;
        final HttpTopicType type = HttpTopicType.Data;
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());

        topic.setMediaType(mediaType);

        assertThat(topic.getMediaType()).isEqualTo(mediaType);
        assertThat(topic.getType()).isEqualTo(type);
    }

    @Test
    public void mediaTypeTextAutoSetTypeData() {
        final MediaType mediaType = MediaType.TEXT_CALENDAR;
        final HttpTopicType type = HttpTopicType.Data;
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());

        topic.setMediaType(mediaType);

        assertThat(topic.getMediaType()).isEqualTo(mediaType);
        assertThat(topic.getType()).isEqualTo(type);
    }

    @Test
    public void mediaTypeTextHTMLAutoSetTypeWebsite() {
        final MediaType mediaType = MediaType.TEXT_HTML;
        final HttpTopicType type = HttpTopicType.Website;
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());

        topic.setMediaType(mediaType);

        assertThat(topic.getMediaType()).isEqualTo(mediaType);
        assertThat(topic.getType()).isEqualTo(type);
    }

    @Test
    public void mediaTypeTextXMLAutoSetTypeWebsite() {
        final MediaType mediaType = MediaType.TEXT_XML;
        final HttpTopicType type = HttpTopicType.Website;
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());

        topic.setMediaType(mediaType);

        assertThat(topic.getMediaType()).isEqualTo(mediaType);
        assertThat(topic.getType()).isEqualTo(type);
    }

    @Test
    public void mediaTypeAutoSetTypeOther() {
        final MediaType mediaType = MediaType.MESSAGE_ALL;
        final HttpTopicType type = HttpTopicType.Other;
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());

        topic.setMediaType(mediaType);

        assertThat(topic.getMediaType()).isEqualTo(mediaType);
        assertThat(topic.getType()).isEqualTo(type);
    }

    @Test
    public void mediaTypeAutoSetTypeVideo() {
        final MediaType mediaType = MediaType.VIDEO_AVI;
        final HttpTopicType type = HttpTopicType.Video;
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());

        topic.setMediaType(mediaType);

        assertThat(topic.getMediaType()).isEqualTo(mediaType);
        assertThat(topic.getType()).isEqualTo(type);
    }

    @Test
    public void addUrlRequestAlchemyIfTypeWebsite() {
        bus.capture(AlchemyRequestEvent.class);
        final Uri uri = new Uri("http://www.test.com");
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());
        topic.setType(HttpTopicType.Website);

        topic.addUri(uri);

        final AlchemyRequestEvent alchemyRequestEvent = bus.lastEvent(AlchemyRequestEvent.class);
        assertThat(alchemyRequestEvent).isNotNull();
        assertThat(alchemyRequestEvent.getHttpTopic()).isEqualTo(topic);
    }

    @Test
    public void doNotRequestAlchemyIfTypeDifferentFromWebsite() {
        bus.capture(AlchemyRequestEvent.class);
        final Uri uri = new Uri("http://www.test.com");
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());
        topic.setType(HttpTopicType.Image);

        topic.addUri(uri);

        final AlchemyRequestEvent alchemyRequestEvent = bus.lastEvent(AlchemyRequestEvent.class);
        assertThat(alchemyRequestEvent).isNull();
    }

    @Test
    public void addUrlAddName() {
        final Uri uri = new Uri("http://www.test.com");
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());
        topic.setType(HttpTopicType.Image);

        topic.addUri(uri);

        assertThat(topic.getNames()).isNotEmpty();
    }

    @Test
    public void addUrlDoNotAddANameForWebsite() {
        final Uri uri = new Uri("http://www.test.com");
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());
        topic.setType(HttpTopicType.Website);

        topic.addUri(uri);

        assertThat(topic.getNames()).isEmpty();
    }

    @Test
    public void canUseRelevantPartOfUrlForName() {
        final Uri uri = new Uri("http://www.test.com");
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());
        topic.setType(HttpTopicType.Image);

        topic.addUri(uri);

        assertThat(topic.getName(FeelhubLanguage.none())).isEqualTo("www.test.com");
    }

    @Test
    public void canUseRelevantPartOfComplexUrlForName() {
        final Uri uri = new Uri("http://4.bp.blogspot.com/-w0o0maaaar8/tbj1tjirv7i/aaaaaaaaafu/ykidnobaxqu/s1600/javascript.jpg");
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());
        topic.setType(HttpTopicType.Image);

        topic.addUri(uri);

        assertThat(topic.getName(FeelhubLanguage.none())).isEqualTo("javascript.jpg");
    }
}