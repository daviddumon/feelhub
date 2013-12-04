package com.feelhub.domain.cloudinary;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.topic.*;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Maps;
import com.google.common.eventbus.*;
import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Cloudinary {

    @Inject
    public Cloudinary(final CloudinaryLink cloudinaryLink) {
        this.cloudinaryLink = cloudinaryLink;
        DomainEventBus.INSTANCE.register(this);
        rateLimiter = RateLimiter.create(5.0);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onThumbnailCreatedEvent(final ThumbnailCreatedEvent thumbnailCreatedEvent) {
        rateLimiter.acquire();
        final Topic topic = Repositories.topics().getCurrentTopic(thumbnailCreatedEvent.getTopicId());
        final Thumbnail thumbnail = getThumbnailWithCloudinaryImage(thumbnailCreatedEvent.getThumbnails());
        topic.addThumbnail(thumbnail);
        topic.setThumbnail(thumbnail.getCloudinary());
    }

    private Thumbnail getThumbnailWithCloudinaryImage(List<Thumbnail> thumbnails) {
        for (Thumbnail thumbnail : thumbnails) {
            try {
                final String cloudinaryImage = getCloudinaryImage(thumbnail);
                thumbnail.setCloudinary(cloudinaryImage);
                return thumbnail;
            } catch (CloudinaryException e) {
                if (thumbnail.equals(thumbnails.get(thumbnails.size() - 1))) {
                    throw e;
                }
            }
        }
        return null;
    }

    public String getCloudinaryImage(final Thumbnail source) {
        final Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_564,h_348,c_fill,g_face,q_75");
        params.put("file", source.getOrigin());
        try {
            return cloudinaryLink.getIllustration(params);
        } catch (IOException e) {
            throw new CloudinaryException(e);
        }
    }

    private final CloudinaryLink cloudinaryLink;
    final RateLimiter rateLimiter;
}
