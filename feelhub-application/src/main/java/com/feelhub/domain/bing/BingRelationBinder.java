package com.feelhub.domain.bing;

import com.feelhub.domain.media.MediaBuilder;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.google.inject.Inject;

import java.util.List;

public class BingRelationBinder {

    @Inject
    public BingRelationBinder(final MediaBuilder mediaBuilder) {
        this.mediaBuilder = mediaBuilder;
    }

    public void bind(final Topic topic, final List<HttpTopic> medias) {
        for (final HttpTopic media : medias) {
            mediaBuilder.connectTwoWays(topic, media);
            mediaBuilder.connectOneWayWithMedia(media, media);
        }
    }

    private final MediaBuilder mediaBuilder;
}
