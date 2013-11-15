package com.feelhub.domain.cloudinary;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.topic.*;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Maps;
import com.google.common.eventbus.*;
import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.Map;

public class Cloudinary {

    @Inject
    public Cloudinary(final CloudinaryLink cloudinaryLink) {
        this.cloudinaryLink = cloudinaryLink;
        DomainEventBus.INSTANCE.register(this);
        final RateLimiter rateLimiter = RateLimiter.create(5.0);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onThumbnailCreatedEvent(final ThumbnailCreatedEvent thumbnailCreatedEvent) {
        final Topic topic = Repositories.topics().getCurrentTopic(thumbnailCreatedEvent.getTopicId());
        final Thumbnail thumbnail = thumbnailCreatedEvent.getThumbnail();
        final String cloudinaryImage = getCloudinaryImage(thumbnail.getOrigin());
        thumbnail.setCloudinary(cloudinaryImage);
        topic.addThumbnail(thumbnail);
        topic.setThumbnail(cloudinaryImage);
    }

    public String getCloudinaryImage(final String source) {
        final Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_564,h_348,c_fill,g_face,q_75");
        params.put("file", source);
        try {
            return cloudinaryLink.getIllustration(params);
        } catch (IOException e) {
            throw new CloudinaryException();
        }
    }

    private final CloudinaryLink cloudinaryLink;
}
