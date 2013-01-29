package com.feelhub.domain.media;

import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;

import java.util.UUID;

public class MediaTestFactory {

    public Media newMedia() {
        return newMedia(TestFactories.topics().newCompleteRealTopic().getId(), TestFactories.topics().newCompleteRealTopic().getId());
    }

    public Media newMedia(final UUID fromId, final UUID toId) {
        final Media media = new Media(fromId, toId);
        Repositories.medias().add(media);
        return media;
    }

    public void newMediaList(final int quantity) {
        newMediaList(quantity, TestFactories.topics().newCompleteRealTopic().getId());
    }

    public void newMediaList(final int quantity, final UUID fromid) {
        for (int i = 0; i < quantity; i++) {
            newMedia(fromid, TestFactories.topics().newCompleteRealTopic().getId());
        }
    }
}
