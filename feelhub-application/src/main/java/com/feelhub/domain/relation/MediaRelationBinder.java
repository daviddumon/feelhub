package com.feelhub.domain.relation;

import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.google.inject.Inject;

import java.util.List;

public class MediaRelationBinder {

    @Inject
    public MediaRelationBinder(final RelationBuilder relationBuilder) {
        this.relationBuilder = relationBuilder;
    }

    public void bind(final Topic topic, final List<HttpTopic> medias) {
        connectTopicToImages(topic, medias);
        connectImages(medias);
    }

    private void connectTopicToImages(final Topic topic, final List<HttpTopic> medias) {
        for (final HttpTopic media : medias) {
            relationBuilder.connectTwoWays(topic, media);
        }
    }

    private void connectImages(final List<HttpTopic> medias) {
        for (int i = 0; i < medias.size(); i++) {
            final Topic currentImage = medias.get(i);
            connectTopic(currentImage, i + 1, medias);
        }
    }

    private void connectTopic(final Topic from, final int beginningIndex, final List<HttpTopic> medias) {
        for (int i = beginningIndex; i < medias.size(); i++) {
            final Topic to = medias.get(i);
            relationBuilder.connectTwoWays(from, to);
        }
    }

    private final RelationBuilder relationBuilder;
}
