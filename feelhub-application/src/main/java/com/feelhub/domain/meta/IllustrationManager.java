package com.feelhub.domain.meta;

import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;

import java.util.*;

public class IllustrationManager {

    public void merge(final TopicPatch topicPatch) {
        final List<UUID> topicIdList = getTopicIdList(topicPatch);
        final List<Illustration> illustrations = getAllIllustrations(topicIdList);
        if (!illustrations.isEmpty()) {
            migrateExistingIllustrations(illustrations, topicPatch.getNewTopicId());
            removeDuplicate(illustrations);
        }
    }

    private List<UUID> getTopicIdList(final TopicPatch topicPatch) {
        final List<UUID> topicIdList = Lists.newArrayList();
        topicIdList.addAll(topicPatch.getOldTopicIds());
        topicIdList.add(topicPatch.getNewTopicId());
        return topicIdList;
    }

    private List<Illustration> getAllIllustrations(final List<UUID> topicIds) {
        final List<Illustration> illustrations = Lists.newArrayList();
        for (final UUID topicId : topicIds) {
            final Illustration illustration = getIllustrationFor(topicId);
            if (illustration != null) {
                illustrations.add(illustration);
            }
        }
        return illustrations;
    }

    private Illustration getIllustrationFor(final UUID topicId) {
        final List<Illustration> illustrations = Repositories.illustrations().forTopicId(topicId);
        if (!illustrations.isEmpty()) {
            return illustrations.get(0);
        } else {
            return null;
        }
    }

    private void migrateExistingIllustrations(final List<Illustration> illustrations, final UUID newTopic) {
        for (final Illustration illustration : illustrations) {
            if (illustration.getTopicId() != newTopic) {
                illustration.setTopicId(newTopic);
            }
        }
    }

    private void removeDuplicate(final List<Illustration> illustrations) {
        if (illustrations.size() > 1) {
            for (int i = 1; i < illustrations.size(); i++) {
                Repositories.illustrations().delete(illustrations.get(i));
            }
        }
    }
}
