package com.feelhub.domain.topic.http;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import org.junit.*;
import org.restlet.data.MediaType;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsHttpTopic {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

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
}

//@Test
//public void requestAlchemy() {
//    bus.capture(AlchemyRequestEvent.class);
//    final String value = "http://www.test.com";
//
//    final RealTopic realTopic = topicFromUriService.createTopicFromUri(value);
//
//    final AlchemyRequestEvent alchemyRequestEvent = bus.lastEvent(AlchemyRequestEvent.class);
//    assertThat(alchemyRequestEvent).isNotNull();
//    assertThat(alchemyRequestEvent.getRealTopic()).isEqualTo(realTopic);
//    assertThat(alchemyRequestEvent.getValue()).isEqualTo(value);
//}