package com.feelhub.domain.relation;

import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.google.inject.Inject;

import java.util.List;

public class BingRelationBinder {

    @Inject
    public BingRelationBinder(final RelationBuilder relationBuilder) {
        this.relationBuilder = relationBuilder;
    }

    public void bind(final Topic topic, final List<HttpTopic> images) {
        connectTopicToImages(topic, images);
        connectImages(images);
    }

    private void connectTopicToImages(final Topic topic, final List<HttpTopic> images) {
        for (final HttpTopic image : images) {
            relationBuilder.connectTwoWays(topic, image);
        }
    }

    private void connectImages(final List<HttpTopic> images) {
        for (int i = 0; i < images.size(); i++) {
            final Topic currentImage = images.get(i);
            connectTopic(currentImage, i + 1, images);
        }
    }

    private void connectTopic(final Topic from, final int beginningIndex, final List<HttpTopic> images) {
        for (int i = beginningIndex; i < images.size(); i++) {
            final Topic to = images.get(i);
            relationBuilder.connectTwoWays(from, to);
        }
    }

    private final RelationBuilder relationBuilder;
}
