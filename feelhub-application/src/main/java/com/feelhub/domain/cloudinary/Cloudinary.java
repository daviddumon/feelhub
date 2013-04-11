package com.feelhub.domain.cloudinary;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.media.MediaCreatedEvent;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopic;
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
    public void onMediaCreatedEvent(final MediaCreatedEvent mediaCreatedEvent) {
        final Topic topic = Repositories.topics().getCurrentTopic(mediaCreatedEvent.getFromId());
        final HttpTopic image = Repositories.topics().getHttpTopic(mediaCreatedEvent.getToId());
        final String thumbnail = getThumbnail(image.getIllustration());
        image.setThumbnail(thumbnail);
        topic.setIllustrationAndThumbnail(image);
    }

    public String getThumbnail(final String source) {
        final Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_564,h_348,c_fill,g_face,q_80");
        params.put("file", source);
        try {
            return cloudinaryLink.getIllustration(params);
        } catch (IOException e) {
            throw new CloudinaryException();
        }
    }

    private final CloudinaryLink cloudinaryLink;
}
