package com.feelhub.domain.bing;

import com.feelhub.domain.cloudinary.CloudinaryException;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.*;
import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.Inject;

import java.util.List;

public class BingSearch {

    @Inject
    public BingSearch(final BingLink bingLink, final UriResolver uriResolver) {
        this.bingLink = bingLink;
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

    public void doBingSearch(final Topic topic, final String query) {
        final List<String> illustrations = bingLink.getIllustrations(query);
        final Thumbnail thumbnail = findThumbnail(illustrations);
        if (thumbnail != null) {
            postThumbnailCreatedEvent(topic, thumbnail);
        }
    }

    private Thumbnail findThumbnail(final List<String> illustrations) {
        int i = 0;
        while (i < illustrations.size()) {
            final String illustration = illustrations.get(i++);
            try {
                final ResolverResult resolverResult = uriResolver.resolve(new Uri(illustration));
                final Thumbnail thumbnail = new Thumbnail();
                thumbnail.setOrigin(getCanonical(resolverResult).toString());
                return thumbnail;
            } catch (UriException e) {
            } catch (TopicException e) {
            } catch (CloudinaryException e) {
            }
        }
        return null;
    }

    private void postThumbnailCreatedEvent(final Topic topic, final Thumbnail thumbnail) {
        final ThumbnailCreatedEvent event = new ThumbnailCreatedEvent();
        event.setTopicId(topic.getCurrentId());
        event.setThumbnail(thumbnail);
        DomainEventBus.INSTANCE.post(event);
    }

    private Uri getCanonical(final ResolverResult resolverResult) {
        return resolverResult.getPath().get(resolverResult.getPath().size() - 1);
    }

    private final BingLink bingLink;
    private final RateLimiter rateLimiter;
    UriResolver uriResolver = new UriResolver();
}
