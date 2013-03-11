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
    public void onMediaCreatedEvent(final MediaCreatedEvent mediaCreatedEvent) {
        final Topic topic = Repositories.topics().getCurrentTopic(mediaCreatedEvent.getFromId());
        final HttpTopic image = Repositories.topics().getHttpTopic(mediaCreatedEvent.getToId());
        final CloudinaryThumbnails thumbnails = getThumbnails(image.getIllustration());
        setThumbnails(image, thumbnails);
        topic.setIllustrations(image);
    }

    public CloudinaryThumbnails getThumbnails(final String source) {
        try {
            final CloudinaryThumbnails cloudinaryThumbnails = new CloudinaryThumbnails();
            cloudinaryThumbnails.setThumbnailLarge(getThumbnailLarge(source));
            cloudinaryThumbnails.setThumbnailMedium(getThumbnailMedium(source));
            cloudinaryThumbnails.setThumbnailSmall(getThumbnailSmall(source));
            return cloudinaryThumbnails;
        } catch (Exception e) {
            throw new CloudinaryException();
        }
    }

    private String getThumbnailLarge(final String source) throws Exception {
        final Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_272,h_168,c_fill,g_face,q_60");
        params.put("file", source);
        return cloudinaryLink.getIllustration(params);
    }

    private String getThumbnailMedium(final String source) throws Exception {
        final Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_135,h_168,c_fill,g_face,q_60");
        params.put("file", source);
        return cloudinaryLink.getIllustration(params);
    }

    private String getThumbnailSmall(final String source) throws Exception {
        final Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_90,h_56,c_fill,g_face,q_60");
        params.put("file", source);
        return cloudinaryLink.getIllustration(params);
    }

    private void setThumbnails(final Topic topic, final CloudinaryThumbnails thumbnails) {
        topic.setThumbnailLarge(thumbnails.getThumbnailLarge());
        topic.setThumbnailMedium(thumbnails.getThumbnailMedium());
        topic.setThumbnailSmall(thumbnails.getThumbnailSmall());
    }

    private final CloudinaryLink cloudinaryLink;
    private final RateLimiter rateLimiter;
}
