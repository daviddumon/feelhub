package com.feelhub.domain.bing;

import com.feelhub.domain.cloudinary.*;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.Inject;
import org.restlet.data.MediaType;

import java.util.List;

public class BingSearch {

    @Inject
    public BingSearch(final BingLink bingLink, final BingRelationBinder bingRelationBinder, final Cloudinary cloudinary) {
        this.bingLink = bingLink;
        this.bingRelationBinder = bingRelationBinder;
        this.cloudinary = cloudinary;
        DomainEventBus.INSTANCE.register(this);
        rateLimiter = RateLimiter.create(5.0);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onRealTopicCreated(final RealTopicCreatedEvent event) {
        rateLimiter.acquire();
        final RealTopic realTopic = Repositories.topics().getRealTopic(event.topicId);
        doBingSearch(realTopic, realTopic.getName(FeelhubLanguage.none()));
    }

    void doBingSearch(final Topic topic, final String query) {
        final List<HttpTopic> images = getImages(topic, query);
        topic.setIllustrations(images);
        bingRelationBinder.bind(topic, images);
    }

    private List<HttpTopic> getImages(final Topic topic, final String query) {
        final List<HttpTopic> images = Lists.newArrayList();
        final List<String> illustrations = bingLink.getIllustrations(query, topic.getType().toString());

        int i = 0;
        while (i < illustrations.size() && images.size() < 5) {
            final String illustration = illustrations.get(i++);
            try {
                final HttpTopic image = createImage(illustration);
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
        final CloudinaryThumbnails thumbnails = cloudinary.getThumbnails(illustration);
        image.setThumbnailLarge(thumbnails.getThumbnailLarge());
        image.setThumbnailMedium(thumbnails.getThumbnailMedium());
        image.setThumbnailSmall(thumbnails.getThumbnailSmall());
        Repositories.topics().add(image);
        return image;
    }

    private final BingLink bingLink;
    private final BingRelationBinder bingRelationBinder;
    private final Cloudinary cloudinary;
    private final RateLimiter rateLimiter;
    UriResolver uriResolver = new UriResolver();
}
