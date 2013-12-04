package com.feelhub.domain.topic;

import com.feelhub.domain.eventbus.DomainEvent;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ThumbnailCreatedEvent extends DomainEvent {

    @Override
    public String toString() {
        return "";
    }

    public UUID getTopicId() {
        return topicId;
    }

    public void setTopicId(final UUID topicId) {
        this.topicId = topicId;
    }

    public List<Thumbnail> getThumbnails() {
        return thumbnails;
    }

    public void addThumbnails(final Thumbnail... thumbnails) {
        this.thumbnails.addAll(Arrays.asList(thumbnails));
    }

    private UUID topicId;
    private List<Thumbnail> thumbnails = Lists.newArrayList();
}
