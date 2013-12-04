package com.feelhub.domain.bing;

import com.feelhub.domain.cloudinary.CloudinaryException;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.real.RealTopicThumbnailUpdateRequestedEvent;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.*;
import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.Inject;

import java.util.List;
import java.util.UUID;

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
        doBingSearch(event.topicId, event.feelhubLanguage);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onRealTopicThumbnailUpdateRequested(final RealTopicThumbnailUpdateRequestedEvent event) {
        doBingSearch(event.topicId, event.feelhubLanguage);
    }

    private void doBingSearch(UUID topicId, FeelhubLanguage feelhubLanguage) {
        rateLimiter.acquire();
        final RealTopic realTopic = Repositories.topics().getRealTopic(topicId);
        doBingSearch(realTopic, realTopic.getName(feelhubLanguage));
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
                return new Thumbnail(getCanonical(resolverResult).toString());
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
        event.addThumbnails(thumbnail);
        DomainEventBus.INSTANCE.post(event);
    }

    private Uri getCanonical(final ResolverResult resolverResult) {
        return resolverResult.getPath().get(resolverResult.getPath().size() - 1);
    }

    private final BingLink bingLink;
    private final RateLimiter rateLimiter;
    UriResolver uriResolver = new UriResolver();
}
