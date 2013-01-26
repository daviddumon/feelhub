package com.feelhub.domain.bingsearch;

import com.feelhub.application.TopicService;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.relation.MediaRelationBinder;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.UriException;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import org.restlet.data.MediaType;

import java.util.List;

public class BingSearch {

    @Inject
    public BingSearch(final BingLink bingLink, final TopicService topicService, final MediaRelationBinder mediaRelationBinder) {
        this.bingLink = bingLink;
        this.topicService = topicService;
        this.mediaRelationBinder = mediaRelationBinder;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void onBingRequest(final BingRequest bingRequest) {
        doBingSearch(bingRequest);
    }

    private void doBingSearch(final BingRequest bingRequest) {
        //todo bug race condition
        //final Topic topic = bingRequest.getTopic();
        final String query = bingRequest.getQuery();
        //final List<HttpTopic> images = getImages(topic, query);
        final List<HttpTopic> images = getImages(bingRequest.getTopic(), query);
        final Topic topic = topicService.lookUpCurrent(bingRequest.getTopicId());
        setIllustrationForTopic(topic, images);
        mediaRelationBinder.bind(topic, images);
    }

    private List<HttpTopic> getImages(final Topic topic, final String query) {
        final List<HttpTopic> images = Lists.newArrayList();
        final List<String> illustrations = bingLink.getIllustrations(query, topic.getType().toString());
        for (final String illustration : illustrations) {
            try {
                final HttpTopic image = createImage(illustration);
                images.add(image);
            } catch (UriException e) {
            } catch (TopicException e) {
            }
        }
        return images;
    }

    private HttpTopic createImage(final String illustration) {
        final HttpTopic image = topicService.createHttpTopicWithRestrictedMediaType(illustration, MediaType.IMAGE_ALL);
        image.setIllustration(illustration);
        image.addName(FeelhubLanguage.none(), illustration);
        return image;
    }

    private void setIllustrationForTopic(final Topic topic, final List<HttpTopic> images) {
        if (topic.getIllustration().isEmpty() && !images.isEmpty()) {
            topic.setIllustration(images.get(0).getIllustration());
        }
    }

    private final BingLink bingLink;
    private final TopicService topicService;
    private final MediaRelationBinder mediaRelationBinder;
}
