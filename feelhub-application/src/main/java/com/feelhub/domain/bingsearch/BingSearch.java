package com.feelhub.domain.bingsearch;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.relation.BingRelationBinder;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.UriException;
import com.feelhub.repositories.*;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import java.util.List;

public class BingSearch {

    @Inject
    public BingSearch(final SessionProvider sessionProvider, final BingLink bingLink, final TopicFactory topicFactory, final BingRelationBinder bingRelationBinder) {
        this.sessionProvider = sessionProvider;
        this.bingLink = bingLink;
        this.topicFactory = topicFactory;
        this.bingRelationBinder = bingRelationBinder;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void onBingRequest(final BingRequest bingRequest) {
        sessionProvider.start();
        doBingSearch(bingRequest);
        sessionProvider.stop();
    }

    private void doBingSearch(final BingRequest bingRequest) {
        final Topic topic = bingRequest.getTopic();
        final String query = bingRequest.getQuery();
        final List<HttpTopic> images = getImages(topic, query);
        setIllustrationForTopic(topic, images);
        bindImagesToQuery(images, query);
        bingRelationBinder.bind(topic, images);
    }

    private List<HttpTopic> getImages(final Topic topic, final String query) {
        final List<HttpTopic> images = Lists.newArrayList();
        final List<String> illustrations = bingLink.getIllustrations(query, topic.getType().toString());
        for (final String illustration : illustrations) {
            try {
                final HttpTopic image = createImage(illustration);
                images.add(image);
            } catch (UriException e) {
            }
        }
        return images;
    }

    private HttpTopic createImage(final String illustration) {
        final HttpTopic image = topicFactory.createHttpTopic(illustration);
        image.setIllustrationLink(illustration);
        image.addName(FeelhubLanguage.none(), illustration);
        image.createTags(illustration);
        Repositories.topics().add(image);
        return image;
    }

    private void setIllustrationForTopic(final Topic topic, final List<HttpTopic> images) {
        if (topic.getIllustrationLink().isEmpty() && !images.isEmpty()) {
            topic.setIllustrationLink(images.get(0).getIllustrationLink());
        }
    }

    private void bindImagesToQuery(final List<HttpTopic> images, final String query) {
        for (final HttpTopic image : images) {
            image.createTags(query);
        }
    }

    private final SessionProvider sessionProvider;
    private final BingLink bingLink;
    private final TopicFactory topicFactory;
    private final BingRelationBinder bingRelationBinder;
}
