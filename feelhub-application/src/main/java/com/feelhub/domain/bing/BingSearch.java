package com.feelhub.domain.bing;

import com.feelhub.application.TopicService;
import com.feelhub.domain.cloudinary.*;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.UriException;
import com.feelhub.tools.MongoLinkAwareExecutor;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.Inject;
import org.restlet.data.MediaType;

import java.util.List;

public class BingSearch {

    @Inject
    public BingSearch(final BingLink bingLink, final TopicService topicService, final BingRelationBinder bingRelationBinder, final Cloudinary cloudinary, final MongoLinkAwareExecutor mongoLinkAwareExecutor) {
        this.bingLink = bingLink;
        this.topicService = topicService;
        this.bingRelationBinder = bingRelationBinder;
        this.cloudinary = cloudinary;
        this.mongoLinkAwareExecutor = mongoLinkAwareExecutor;
        DomainEventBus.INSTANCE.register(this);
        rateLimiter = RateLimiter.create(1.0);
    }

    @Subscribe
    public void onBingRequest(final BingRequest bingRequest) {
        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                doBingSearch(bingRequest);
            }
        };
        rateLimiter.acquire();
        mongoLinkAwareExecutor.execute(runnable);
    }

    private void doBingSearch(final BingRequest bingRequest) {
        //todo bug race condition
        //final Topic topic = bingRequest.getTopic();
        final String query = bingRequest.getQuery();
        //final List<HttpTopic> images = getImages(topic, query);
        final List<HttpTopic> images = getImages(bingRequest.getTopic(), query);
        final Topic topic = topicService.lookUpCurrent(bingRequest.getTopicId());
        setIllustrationForTopic(topic, images);
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
        final HttpTopic image = topicService.createHttpTopicWithRestrictedMediaType(illustration, MediaType.IMAGE_ALL);
        image.setIllustration(illustration);
        final CloudinaryThumbnails thumbnails = cloudinary.getThumbnails(illustration);
        image.setThumbnailLarge(thumbnails.getThumbnailLarge());
        image.setThumbnailMedium(thumbnails.getThumbnailMedium());
        image.setThumbnailSmall(thumbnails.getThumbnailSmall());
        return image;
    }

    private void setIllustrationForTopic(final Topic topic, final List<HttpTopic> images) {
        if (topic.getIllustration().isEmpty() && !images.isEmpty()) {
            topic.setIllustration(images.get(0).getIllustration());
            topic.setThumbnailLarge(images.get(0).getThumbnailLarge());
            topic.setThumbnailMedium(images.get(0).getThumbnailMedium());
            topic.setThumbnailSmall(images.get(0).getThumbnailSmall());
        }
    }

    private final BingLink bingLink;
    private final TopicService topicService;
    private final BingRelationBinder bingRelationBinder;
    private final Cloudinary cloudinary;
    private MongoLinkAwareExecutor mongoLinkAwareExecutor;
    private final RateLimiter rateLimiter;
}
