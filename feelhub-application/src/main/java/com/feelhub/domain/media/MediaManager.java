package com.feelhub.domain.media;

import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class MediaManager {

    public void merge(final TopicPatch topicPatch) {
        for (final UUID oldTopicId : topicPatch.getOldTopicIds()) {
            final List<Media> mediaList = Repositories.medias().containingTopicId(oldTopicId);
            if (!mediaList.isEmpty()) {
                migrateMedias(topicPatch.getNewTopicId(), oldTopicId, mediaList);
            }
        }
    }

    private void migrateMedias(final UUID newTopicId, final UUID oldTopicId, final List<Media> mediaList) {
        for (final Media media : mediaList) {
            checkFromId(newTopicId, oldTopicId, media);
            checkToId(newTopicId, oldTopicId, media);
        }
    }

    private void checkFromId(final UUID newTopicId, final UUID oldTopicId, final Media media) {
        if (media.getFromId().equals(oldTopicId)) {
            if (media.getToId().equals(newTopicId)) {
                Repositories.medias().delete(media);
            } else {
                final Media mediaFound = Repositories.medias().lookUp(newTopicId, media.getToId());
                if (mediaFound == null) {
                    media.setFromId(newTopicId);
                } else {
                    Repositories.medias().delete(media);
                }
            }
        }
    }

    private void checkToId(final UUID newTopicId, final UUID oldTopicId, final Media media) {
        if (media.getToId().equals(oldTopicId)) {
            if (media.getFromId().equals(newTopicId)) {
                Repositories.medias().delete(media);
            } else {
                final Media mediaFound = Repositories.medias().lookUp(media.getFromId(), newTopicId);
                if (mediaFound == null) {
                    media.setToId(newTopicId);
                } else {
                    Repositories.medias().delete(media);
                }
            }
        }
    }
}
