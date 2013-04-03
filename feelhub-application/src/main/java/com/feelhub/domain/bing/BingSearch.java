package com.feelhub.domain.bing;

import com.feelhub.domain.cloudinary.CloudinaryException;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.media.MediaCreatedEvent;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.common.eventbus.*;
import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.Inject;
import org.restlet.data.MediaType;

import java.util.List;

public class BingSearch {

    @Inject
    public BingSearch(final BingLink bingLink, final BingRelationBinder bingRelationBinder) {
        this.bingLink = bingLink;
        this.bingRelationBinder = bingRelationBinder;
        DomainEventBus.INSTANCE.register(this);
        rateLimiter = RateLimiter.create(5.0);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onRealTopicCreated(final RealTopicCreatedEvent event) {
        rateLimiter.acquire();
        final RealTopic realTopic = Repositories.topics().getRealTopic(event.topicId);
        doBingSearch(realTopic, realTopic.getName(event.feelhubLanguage));
    }

    void doBingSearch(final Topic topic, final String query) {
        final List<HttpTopic> images = getImages(topic, query);
        bingRelationBinder.bind(topic, images);
    }

    private List<HttpTopic> getImages(final Topic topic, final String query) {
        final List<HttpTopic> images = Lists.newArrayList();
        final List<String> illustrations = bingLink.getIllustrations(query);

        int i = 0;
        while (i < illustrations.size() && images.size() < 5) {
            final String illustration = illustrations.get(i++);
            try {
                final HttpTopic image = createImage(illustration);
                DomainEventBus.INSTANCE.post(new MediaCreatedEvent(topic.getCurrentId(), image.getCurrentId()));
                images.add(image);
            } catch (UriException e) {
            } catch (TopicException e) {
            } catch (CloudinaryException e) {
            }
        }
        return images;
    }

    private HttpTopic createImage(final String illustration) {
        final HttpTopic image = new TopicFactory().createHttpTopicWithMediaType(illustration, MediaType.IMAGE_ALL, uriResolver);
        image.setIllustration(illustration);
        Repositories.topics().add(image);
        return image;
    }

    private final BingLink bingLink;
    private final BingRelationBinder bingRelationBinder;
    private final RateLimiter rateLimiter;
    UriResolver uriResolver = new UriResolver();
}
